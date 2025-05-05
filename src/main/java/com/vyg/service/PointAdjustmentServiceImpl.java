package com.vyg.service;

import com.vyg.dto.PointAdjustmentRequest;
import com.vyg.entity.*;
import com.vyg.exception.ResourceNotFoundException;
import com.vyg.mapper.PointsAdjustmentMapper;
import com.vyg.repository.AddressRepository;
import com.vyg.repository.CapturedPointRepository;
import com.vyg.repository.NationsRepository;
import com.vyg.repository.PointAdjustmentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class PointAdjustmentServiceImpl implements PointAdjustmentService{

    private final NationsRepository nationsRepository;
    private final PointAdjustmentRepository pointAdjustmentRepository;
    private final AddressRepository addressRepository;
    private final CapturedPointRepository capturedPointRepository;

    @Override
    @Transactional
    public void adjustPoints(PointAdjustmentRequest pointAdjustmentRequest) {

        Nations nations = nationsRepository.findById(pointAdjustmentRequest.getNationId())
                .orElseThrow(() -> new ResourceNotFoundException("Nation with with Id not found " + pointAdjustmentRequest.getNationId()));

        Address address = addressRepository.findById(pointAdjustmentRequest.getAddressId())
                .orElseThrow(() -> new ResourceNotFoundException("Address not found: " + pointAdjustmentRequest.getAddressId()));



        int adjustment =  "SUBTRACT".equalsIgnoreCase(pointAdjustmentRequest.getAdjustmentType())
                ?
                -pointAdjustmentRequest.getPoints():
                pointAdjustmentRequest.getPoints();
        nations.setTotalPoints(Math.max(0, nations.getTotalPoints() + adjustment));



//        if("ADD".equalsIgnoreCase(pointAdjustmentRequest.getAdjustmentType())){
//            nations.setTotalPoints(nations.getTotalPoints() + pointAdjustmentRequest.getPoints());
//
//
//        } else if ("SUBTRACT".equalsIgnoreCase(pointAdjustmentRequest.getAdjustmentType())) {
//            nations.setTotalPoints(Math.max(0, nations.getTotalPoints()) - pointAdjustmentRequest.getPoints());


        PointAdjustment entity = PointsAdjustmentMapper.toEntity(pointAdjustmentRequest,address,nations);
        pointAdjustmentRepository.save(entity);
        nationsRepository.save(nations);

        CapturedPoint cp = CapturedPoint.builder()
                .nation(nations)
                .address(address)
                .baseEvent(null) // Optional: consider creating a "Manual Adjustment" BaseEvent
                .numberOfPeople(0)
                .points(adjustment)
                .totalPointsEarnedPerWeek(adjustment)
                .capturedBy(pointAdjustmentRequest.getCapturedBy())
                .dateCaptured(java.time.LocalDate.now())
                .fullDate(java.time.LocalDate.now().format(
                        java.time.format.DateTimeFormatter.ofPattern("dd MMMM yyyy", java.util.Locale.ENGLISH)))
                .weekNumber(java.time.LocalDate.now().get(java.time.temporal.WeekFields.ISO.weekOfYear()))
                .month(java.time.LocalDate.now().getMonthValue())
                .year(java.time.LocalDate.now().getYear())
                .build();

        capturedPointRepository.save(cp);


    }
}
