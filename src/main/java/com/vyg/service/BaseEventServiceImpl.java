package com.vyg.service;

import com.vyg.entity.BaseEvent;
import com.vyg.exception.ResourceNotFoundException;
import com.vyg.mapper.EventMapper;
import com.vyg.dto.BaseEventRequest;
import com.vyg.repository.BaseEventRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;



@Service
@RequiredArgsConstructor
public class BaseEventServiceImpl implements BaseEventService {

    private final BaseEventRepository baseEventRepository;


    @Override
    public List<BaseEvent> getAllEvents() {
        return  baseEventRepository.findAll();
    }

    @Transactional
    @Override
    public BaseEvent createEvent(BaseEventRequest baseEventRequest) {

        BaseEvent toEntity = EventMapper.toEntity(baseEventRequest);
        baseEventRequest.setShowEvent(true);

        BaseEvent saveBaseEvent = baseEventRepository.save(toEntity);

        return saveBaseEvent;
    }

    @Override
    @Transactional
    public BaseEvent updateEvent(Long id, BaseEventRequest baseEventRequest) {

        BaseEvent event = baseEventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not dound with ID : " + id));

        event.setName(baseEventRequest.getEventName());
        event.setDefaultPoints(baseEventRequest.getDefaultPoints());

        return baseEventRepository.save(event);
    }


}

