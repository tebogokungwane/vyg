package com.vyg.service;

import com.vyg.dto.NationDTO;
import com.vyg.dto.NationPointsRequest;
import com.vyg.entity.Address;
import com.vyg.entity.BaseEvent;
import com.vyg.entity.CapturedPoint;
import com.vyg.entity.NationPoints;
import com.vyg.enumerator.ApprovalStatus;
import com.vyg.enumerator.Nation;
import com.vyg.repository.AddressRepository;
import com.vyg.repository.BaseEventRepository;
import com.vyg.repository.CapturedPointRepository;
import com.vyg.repository.NationPointsRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class NationPointsService {

    private final NationPointsRepository nationPointsRepository;
    private final BaseEventRepository baseEventRepository;
    private final AddressRepository addressRepository;
    private final CapturedPointRepository capturedPointRepository;

    public NationPointsService(
            NationPointsRepository nationPointsRepository,
            BaseEventRepository baseEventRepository,
            AddressRepository addressRepository,
            CapturedPointRepository capturedPointRepository
    ) {
        this.nationPointsRepository = nationPointsRepository;
        this.baseEventRepository = baseEventRepository;
        this.addressRepository = addressRepository;
        this.capturedPointRepository = capturedPointRepository;
    }

    /* ================= ADD POINTS ================= */
    public NationPoints addPoints(NationPointsRequest request) {
        BaseEvent event = baseEventRepository.findById(request.getEventId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Invalid Event ID: " + request.getEventId()
                ));

        Nation nation;
        try {
            nation = Nation.valueOf(request.getNationName().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid nation: " + request.getNationName()
            );
        }

        NationPoints points = NationPoints.builder()
                .nation(nation)
                .event(event)
                .weekNumber(request.getWeekNumber())
                .pointsEarned(event.getDefaultPoints())
                .province(request.getProvince() != null ? request.getProvince().toUpperCase() : null)
                .region(request.getRegion() != null ? request.getRegion().toUpperCase() : null)
                .build();

        return nationPointsRepository.save(points);
    }

    /* ================= TOP PERFORMANCE ================= */
    public List<NationDTO> getTopPerformance(
            Long addressId,
            String viewMode,
            Integer week,
            Integer month,
            Integer year
    ) {
        if (addressId == null || year == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Address ID and year are required"
            );
        }

        if (viewMode == null || viewMode.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "viewMode is required"
            );
        }

        // Fetch province and region from address
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Invalid Address ID: " + addressId
                ));

        String province = address.getProvince().name();
        String region = address.getBranch().name();

        List<Object[]> results;

        switch (viewMode.toLowerCase()) {
            case "weekly" -> {
                if (week == null) {
                    throw new ResponseStatusException(
                            HttpStatus.BAD_REQUEST,
                            "Week is required for weekly view"
                    );
                }
                results = nationPointsRepository.findWeeklyPerformance(province, region, week, year);
            }

            case "monthly" -> {
                if (month == null) {
                    throw new ResponseStatusException(
                            HttpStatus.BAD_REQUEST,
                            "Month is required for monthly view"
                    );
                }
                results = nationPointsRepository.findMonthlyPerformance(province, region, month, year);
            }

            case "yearly" -> results = nationPointsRepository.findYearlyPerformance(province, region, year);

            default -> throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid viewMode: " + viewMode
            );
        }

        return mapToNationDTO(results);
    }

    /* ================= DTO MAPPER ================= */
    private List<NationDTO> mapToNationDTO(List<Object[]> results) {
        List<NationDTO> list = new ArrayList<>();

        for (Object[] row : results) {
            try {
                Nation nation = (row[0] instanceof Nation n) ? n : Nation.valueOf(row[0].toString());
                double totalPoints = ((Number) row[1]).doubleValue();

                NationDTO dto = new NationDTO();
                dto.setNation(nation.name());
                dto.setTotalPoints(totalPoints);
                dto.setImageUrl("/api/nations/image/" + nation.name());

                list.add(dto);
            } catch (Exception e) {
                throw new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Error mapping database results to NationDTO: " + e.getMessage()
                );
            }
        }

        return list;
    }

    @Transactional
    public void approve(Long id, String approvedBy) {

        CapturedPoint point = capturedPointRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CapturedPoint not found: " + id));

        if (point.getApprovalStatus() == ApprovalStatus.APPROVED) {
            throw new RuntimeException("This record is already approved");
        }

        point.setApprovalStatus(ApprovalStatus.APPROVED);
        point.setApprovedBy(approvedBy);
        point.setApprovedDate(LocalDate.now());

        capturedPointRepository.save(point);
    }
}
