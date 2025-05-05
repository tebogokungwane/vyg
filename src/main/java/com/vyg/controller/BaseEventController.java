package com.vyg.controller;


import com.vyg.dto.BaseEventRequest;
import com.vyg.entity.BaseEvent;
import com.vyg.dto.CapturedPointRequestV2;
import com.vyg.service.BaseEventService;
import com.vyg.service.CapturedPointServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/base-events")
public class BaseEventController {

    private final BaseEventService baseEventService;
    private final CapturedPointServiceImpl capturedPointService;

    public BaseEventController(BaseEventService baseEventService, CapturedPointServiceImpl capturedPointService) {
        this.baseEventService = baseEventService;
        this.capturedPointService = capturedPointService;
    }

    @PostMapping("/create")
    public ResponseEntity<BaseEvent> createBaseEvent(@RequestBody BaseEventRequest baseEventRequest){

        BaseEvent event = baseEventService.createEvent(baseEventRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(event);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<BaseEvent> updateEvent(@PathVariable Long id, @RequestBody BaseEventRequest baseEventRequest){
        BaseEvent updateEvent = baseEventService.updateEvent(id, baseEventRequest);
        return ResponseEntity.ok(updateEvent);
    }

    @GetMapping("/allEvents")
    public ResponseEntity<List<BaseEvent>> getAllActiveBaseEvents() {
        return ResponseEntity.ok(baseEventService.getAllEvents());
    }

    @PostMapping("/capture")
    public ResponseEntity<?> capturePoints(@RequestBody CapturedPointRequestV2 request) {
        try {
            capturedPointService.capturePoints(request);
            return ResponseEntity.ok("Points captured successfully");
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Soft delete (disable) a Base Event
     */
//    @DeleteMapping("/disable/{eventId}")
//    public ResponseEntity<String> disableBaseEvent(@PathVariable Long eventId) {
//        baseEventService.disableBaseEvent(eventId);
//        return ResponseEntity.ok("Event disabled successfully!");
//    }
}

