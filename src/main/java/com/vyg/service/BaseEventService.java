package com.vyg.service;


import com.vyg.model.BaseEventRequest;
import com.vyg.entity.BaseEvent;
import com.vyg.repository.BaseEventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BaseEventService {

    private final BaseEventRepository baseEventRepository;

    public BaseEventService(BaseEventRepository baseEventRepository) {
        this.baseEventRepository = baseEventRepository;
    }

    /**
     * Add multiple Base Events
     */
    public List<BaseEvent> createBaseEvents(List<BaseEventRequest> baseEventRequests) {
        List<BaseEvent> baseEvents = baseEventRequests.stream()
                .map(request -> BaseEvent.builder()
                        .name(request.getEventName())
                        .defaultPoints(request.getDefaultPoints())
                        .active(request.isShowEvent())
                        .build())
                .collect(Collectors.toList());

        return baseEventRepository.saveAll(baseEvents);
    }

    /**
     * Get all active Base Events
     */
    public List<BaseEvent> getAllActiveBaseEvents() {
        return baseEventRepository.findByActiveTrue();
    }

    /**
     * Soft delete (disable) a Base Event
     */
    public void disableBaseEvent(Long eventId) {
        BaseEvent baseEvent = baseEventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        baseEvent.setActive(false);
        baseEventRepository.save(baseEvent);
    }
}

