package com.vyg.service;

import com.vyg.dto.ManualAdjustmentRequestDTO;
import com.vyg.entity.CapturedPoint;
import com.vyg.entity.ManualAdjustment;
import com.vyg.enumerator.ApprovalStatus;
import com.vyg.enumerator.CaptureType;
import com.vyg.repository.AddressRepository;
import com.vyg.repository.CapturedPointRepository;
import com.vyg.repository.ManualAdjustmentRepository;
import com.vyg.repository.NationsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ManualAdjustmentService {

    private final ManualAdjustmentRepository manualAdjustmentRepository;
    private final NationsRepository nationRepository;
    private final AddressRepository addressRepository;
    private final CapturedPointRepository capturedPointRepository;


    public List<ManualAdjustment> getPendingAdjustments(Long addressId) {
        return manualAdjustmentRepository.findByAddress_IdAndApprovalStatus(addressId, ApprovalStatus.PENDING);
    }


    @Transactional
    public void createRequest(ManualAdjustmentRequestDTO dto) {

        ManualAdjustment entity = new ManualAdjustment();

        entity.setNation(
                nationRepository.findById(dto.getNationId())
                        .orElseThrow(() -> new IllegalArgumentException("Nation not found"))
        );

        entity.setAddress(
                addressRepository.findById(dto.getAddressId())
                        .orElseThrow(() -> new IllegalArgumentException("Address not found"))
        );

        entity.setPoints(dto.getPoints());
        entity.setReason(dto.getReason());

        entity.setRequestedBy(
                dto.getRequestedBy() != null && !dto.getRequestedBy().isBlank()
                        ? dto.getRequestedBy()
                        : "SYSTEM"
        );

        entity.setApprovalStatus(ApprovalStatus.PENDING);
        entity.setRequestedDate(LocalDate.now());

        manualAdjustmentRepository.save(entity); // ✅ INSERT
    }

    @Transactional
    public void approve(Long adjustmentId, String approvedBy) {

        ManualAdjustment adjustment = manualAdjustmentRepository.findById(adjustmentId)
                .orElseThrow(() -> new RuntimeException("Adjustment not found"));

        if (adjustment.getApprovalStatus() == ApprovalStatus.APPROVED) {
            throw new RuntimeException("Already approved");
        }

        // Find weekly record or create a new one
        CapturedPoint weeklyRecord = capturedPointRepository
                .findByNationAndBaseEventAndWeekNumberAndYearAndApprovalStatus(
                        adjustment.getNation(),
                        adjustment.getBaseEvent(),
                        adjustment.getWeekNumber(),
                        adjustment.getYear(),
                        ApprovalStatus.APPROVED
                )
                .orElseGet(() -> {
                    CapturedPoint cp = new CapturedPoint();
                    cp.setNation(adjustment.getNation());
                    cp.setBaseEvent(adjustment.getBaseEvent());
                    cp.setWeekNumber(adjustment.getWeekNumber());
                    cp.setYear(adjustment.getYear());
                    cp.setTotalPointsEarnedPerWeek(0);
                    cp.setApprovalStatus(ApprovalStatus.APPROVED); // mark as approved
                    cp.setAddress(adjustment.getAddress()); // important for DB
                    cp.setDateCaptured(LocalDate.now());
                    cp.setFullDate(LocalDate.now().toString());
                    return cp;
                });

        // Apply adjustment points
        weeklyRecord.setTotalPointsEarnedPerWeek(
                weeklyRecord.getTotalPointsEarnedPerWeek() + adjustment.getPoints()
        );

        // Approve the adjustment
        adjustment.setApprovalStatus(ApprovalStatus.APPROVED);
        adjustment.setApprovedBy(approvedBy);
        adjustment.setApprovedDate(LocalDate.now());

        capturedPointRepository.save(weeklyRecord);
        manualAdjustmentRepository.save(adjustment);
    }
}