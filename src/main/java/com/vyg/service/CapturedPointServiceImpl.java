package com.vyg.service;

import com.vyg.dto.PendingCapturedPointDTO;
import com.vyg.entity.Address;
import com.vyg.entity.BaseEvent;
import com.vyg.entity.CapturedPoint;
import com.vyg.entity.Nations;
//import com.vyg.model.CapturedPointRequest;
import com.vyg.dto.CapturedPointRequestV2;
//import com.vyg.model.NationPointsSummaryDTO;
import com.vyg.enumerator.ApprovalStatus;
import com.vyg.repository.AddressRepository;
import com.vyg.repository.BaseEventRepository;
import com.vyg.repository.CapturedPointRepository;
import com.vyg.repository.NationsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CapturedPointServiceImpl  {

    private final CapturedPointRepository capturedPointRepository;
    private final BaseEventRepository baseEventRepository;
    private final NationsRepository nationsRepository;
    private final AddressRepository addressRepository;

    @Transactional
    public void capturePoints(CapturedPointRequestV2 request) {
        LocalDate date = request.getDateCaptured();
        int week = date.get(WeekFields.ISO.weekOfYear());
        int year = date.getYear();

        Nations nation = nationsRepository.findById((long) request.getNationId())
                .orElseThrow(() -> new IllegalArgumentException("Nation not found"));

        for (Map.Entry<Integer, Integer> entry : request.getEventAttendance().entrySet()) {
            int eventId = entry.getKey();
            int numberOfPeople = entry.getValue();

            BaseEvent baseEvent = baseEventRepository.findById((long) eventId)
                    .orElseThrow(() -> new IllegalArgumentException("BaseEvent not found for ID: " + eventId));

//            boolean alreadyCaptured = capturedPointRepository.existsByNation_IdAndBaseEvent_IdAndWeekNumberAndYear(
//                    request.getNationId(), baseEvent.getId(), week, year
//            );
            boolean alreadySubmitted =
                    capturedPointRepository.existsByNation_IdAndBaseEvent_IdAndWeekNumberAndYear(
                            (long) request.getNationId(),
                            baseEvent.getId(),
                            week,
                            year
                    );

            boolean alreadyApproved = capturedPointRepository
                    .existsByNation_IdAndBaseEvent_IdAndWeekNumberAndYear(
                            (long) request.getNationId(),
                            baseEvent.getId(),
                            week,
                            year
                   );

            if (alreadyApproved) {
                throw new IllegalStateException(
                        "Points already approved for this event this week."
                );
            }



            Address address = addressRepository.findById(request.getAddressId())
                    .orElseThrow(() -> new IllegalArgumentException("Address not found for ID: " + request.getAddressId()));

            int totalPointsFromThisSubmission = 0;

            int points = numberOfPeople * baseEvent.getDefaultPoints();


            CapturedPoint point = CapturedPoint.builder()
                    .nation(nation)
                    .baseEvent(baseEvent) // ✅ Ensure this is not null!
                    .numberOfPeople(numberOfPeople)
                    .points(baseEvent.getDefaultPoints())
                    .dateCaptured(date)
                    .fullDate(date.format(DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.ENGLISH)))
                    .weekNumber(week)
                    .month(date.getMonthValue())
                    .year(year)
                    .capturedBy(request.getCapturedBy())
                    .address(address) // ✅ SAVE address
                    .totalPointsEarnedPerWeek(numberOfPeople * baseEvent.getDefaultPoints())
                    .approvalStatus(ApprovalStatus.PENDING)
                    .build();

            capturedPointRepository.save(point);

        }
    }


    @Transactional
    public List<CapturedPoint> getNationPointsByAddress(Long addressId) {
        return capturedPointRepository.findAllByAddress_Id(addressId);
    }


    @Transactional
    public Map<String, Object> getTopNationPerformance(Long addressId) {
        List<CapturedPoint> allPoints = capturedPointRepository.findAllByAddress_Id(addressId);

        Map<String, Integer> weekMap = new HashMap<>();
        Map<String, Integer> monthMap = new HashMap<>();
        Map<String, Integer> yearMap = new HashMap<>();

        for (CapturedPoint cp : allPoints) {
            if (cp.getApprovalStatus() != ApprovalStatus.APPROVED) {
                continue;
            }

            String nation = cp.getNation().getNation();
            weekMap.merge(nation, cp.getTotalPointsEarnedPerWeek(), Integer::sum);
            monthMap.merge(nation, cp.getTotalPointsEarnedPerWeek(), Integer::sum);
            yearMap.merge(nation, cp.getTotalPointsEarnedPerWeek(), Integer::sum);
        }


        String topWeek = weekMap.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse("None");
        String topMonth = monthMap.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse("None");
        String topYear = yearMap.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse("None");

        Map<String, Object> result = new HashMap<>();
        result.put("topWeek", topWeek);
        result.put("topMonth", topMonth);
        result.put("topYear", topYear);
        return result;
    }
    public List<PendingCapturedPointDTO> getPendingPointsByAddress(Long addressId) {
        return capturedPointRepository
                .findByAddress_IdAndApprovalStatus(addressId, ApprovalStatus.PENDING)
                .stream()
                .map(cp -> new PendingCapturedPointDTO(
                        cp.getId(),
                        cp.getNation().getNation(),
                        cp.getAddress().getFullAddress(),
                        cp.getAddress().getFullAddress(),
                        cp.getBaseEvent().getName(),
                        cp.getNumberOfPeople(),
                        cp.getPoints(),
                        cp.getTotalPointsEarnedPerWeek(),
                        cp.getDateCaptured(),
                        cp.getCapturedBy()
                ))
                .toList();
    }



    @Transactional
    public void approveCapturedPoint(Long capturedPointId, String approvedBy) {

        CapturedPoint point = capturedPointRepository.findById(capturedPointId)
                .orElseThrow(() -> new IllegalArgumentException("Captured point not found"));

        if (point.getApprovalStatus() != ApprovalStatus.PENDING) {
            throw new IllegalStateException("Point already processed");
        }

        Nations nation = point.getNation();

        nation.setTotalPoints(
                nation.getTotalPoints() + point.getTotalPointsEarnedPerWeek()
        );

        point.setApprovalStatus(ApprovalStatus.APPROVED);
        point.setApprovedBy(approvedBy);
        point.setApprovedDate(LocalDate.now());


        nationsRepository.save(nation);
        capturedPointRepository.save(point);
    }

    @Transactional
    public void rejectCapturedPoint(Long id) {
        CapturedPoint point = capturedPointRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Captured point not found"));
        if (point.getApprovalStatus() != ApprovalStatus.PENDING) {
            throw new IllegalStateException("Only pending points can be rejected");
        }
        capturedPointRepository.delete(point);
    }
}
