package com.vyg.controller;


import com.vyg.model.BaseEventRequest;
import com.vyg.entity.BaseEvent;
import com.vyg.model.CapturedPointRequest;
import com.vyg.service.BaseEventService;
import com.vyg.service.CapturedPointServiceImpl;
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

    /**
     * Create multiple Base Events
     */
//    @PostMapping("/create")
//    public ResponseEntity<List<BaseEvent>> createBaseEvents(@RequestBody List<BaseEventRequest> baseEventRequests) {
//        return ResponseEntity.ok(baseEventService.createBaseEvents(baseEventRequests));
//    }


    @GetMapping("/allEvents")
    public ResponseEntity<List<BaseEvent>> getAllActiveBaseEvents() {
        return ResponseEntity.ok(baseEventService.getAllEvents());
    }

    @PostMapping("/capture")
    public ResponseEntity<?> capturePoints(@RequestBody CapturedPointRequest request) {
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

