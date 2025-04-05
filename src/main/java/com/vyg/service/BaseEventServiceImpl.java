package com.vyg.service;

import com.vyg.entity.BaseEvent;
import com.vyg.enumerator.DefaultBaseEvent;
import com.vyg.model.BaseEventRequest;
import com.vyg.repository.BaseEventRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;



@Service
public class BaseEventServiceImpl implements BaseEventService {

    private final BaseEventRepository baseEventRepository;

    public BaseEventServiceImpl(BaseEventRepository baseEventRepository) {
        this.baseEventRepository = baseEventRepository;
    }

    @Override
    public List<BaseEvent> getAllEvents() {
        return  baseEventRepository.findAll();
    }

//    @PostConstruct
//    @Transactional
//    public void insertBaseEventsOnStartup(){
//        Arrays.stream(DefaultBaseEvent.values()).forEach(eventEnum ->{
//            baseEventRepository.findByName(eventEnum.getEventName()).ifPresentOrElse(
//                    event -> {},
//                    ()->{
//                        BaseEvent newEvent = BaseEvent.builder()
//                                .name(eventEnum.getEventName())
//                                .defaultPoints(eventEnum.getDefaultPoints())
//                                .build();
//                        baseEventRepository.save(newEvent);
//                        System.out.println("Inserted Base Events: "+ eventEnum.getEventName());
//                    }
//            );
//        });
//    }

    /**
     * Add multiple Base Events
     */
//    public List<BaseEvent> createBaseEvents(List<BaseEventRequest> baseEventRequests) {
//        List<BaseEvent> baseEvents = baseEventRequests.stream()
//                .map(request -> BaseEvent.builder()
//                        .name(request.getEventName())
//                        .defaultPoints(request.getDefaultPoints())
//                        .build())
//                .collect(Collectors.toList());
//
//        return baseEventRepository.saveAll(baseEvents);
//    }

    /**
     * Get all active Base Events
     */
//    public List<BaseEvent> getAllActiveBaseEvents() {
//        return baseEventRepository.findByActiveTrue();
//    }

    /**
     * Soft delete (disable) a Base Event
//     */
//    public void disableBaseEvent(Long eventId) {
//        BaseEvent baseEvent = baseEventRepository.findById(eventId)
//                .orElseThrow(() -> new IllegalArgumentException("Event not found"));
//
//        baseEventRepository.save(baseEvent);
//    }
}

