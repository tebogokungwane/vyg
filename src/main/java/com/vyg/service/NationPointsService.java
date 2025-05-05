package com.vyg.service;

import com.vyg.entity.NationPoints;
import com.vyg.entity.BaseEvent;
import com.vyg.enumerator.Nation;
import com.vyg.dto.NationPointsRequest;
import com.vyg.repository.CapturedPointRepository;
import com.vyg.repository.NationPointsRepository;
import com.vyg.repository.BaseEventRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NationPointsService {

    private final NationPointsRepository nationPointsRepository;
    private final BaseEventRepository baseEventRepository;
    private final CapturedPointRepository capturedPointRepository;

    public NationPointsService(NationPointsRepository nationPointsRepository,
                               BaseEventRepository baseEventRepository, CapturedPointRepository capturedPointRepository) {
        this.nationPointsRepository = nationPointsRepository;
        this.baseEventRepository = baseEventRepository;
        this.capturedPointRepository = capturedPointRepository;
    }


    public NationPoints addPoints(NationPointsRequest nationPointsRequest) {
        Optional<BaseEvent> eventOpt = baseEventRepository.findById(nationPointsRequest.getEventId());

        if (eventOpt.isEmpty()) {
            throw new IllegalArgumentException("Invalid Event ID");
        }

        Nation nation = Nation.valueOf(nationPointsRequest.getNationName().toUpperCase());

        NationPoints nationPoints = NationPoints.builder()
                .nation(nation)
                .event(eventOpt.get())
                .weekNumber(nationPointsRequest.getWeekNumber())
                .build();

        return nationPointsRepository.save(nationPoints);
    }


//    public List<NationPointsSummaryDTO> getNationPointsSummaryByAddressId(Long addressId) {
//        return capturedPointRepository.getNationPointsSummaryByAddressId(addressId);
//    }
}
