package com.vyg.service;

import com.vyg.entity.NationPoints;
import com.vyg.entity.BaseEvent;
import com.vyg.enumerator.Nation;
import com.vyg.model.NationPointsRequest;
import com.vyg.repository.NationPointsRepository;
import com.vyg.repository.BaseEventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NationPointsService {

    private final NationPointsRepository nationPointsRepository;
    private final BaseEventRepository baseEventRepository;

    public NationPointsService(NationPointsRepository nationPointsRepository,
                               BaseEventRepository baseEventRepository) {
        this.nationPointsRepository = nationPointsRepository;
        this.baseEventRepository = baseEventRepository;
    }


    public NationPoints addPoints(NationPointsRequest nationPointsRequest) {
        Optional<BaseEvent> eventOpt = baseEventRepository.findById(nationPointsRequest.getEventId());

        if (eventOpt.isEmpty()) {
            throw new IllegalArgumentException("Invalid Event ID");
        }

        Nation nation = Nation.valueOf(nationPointsRequest.getNationName().toUpperCase());

        NationPoints nationPoints = NationPoints.builder()
                .province(nationPointsRequest.getProvince())
                .region(nationPointsRequest.getRegion())
                .nation(nation)
                .event(eventOpt.get())
                .weekNumber(nationPointsRequest.getWeekNumber())
                .pointsEarned(nationPointsRequest.getPoints())
                .build();

        return nationPointsRepository.save(nationPoints);
    }
    public List<NationPoints> getNationPoints(Nation nation) {
        return nationPointsRepository.findByNation(nation);
    }
}
