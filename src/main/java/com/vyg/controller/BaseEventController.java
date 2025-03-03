package com.vyg.controller;


import com.vyg.model.BaseEventRequest;
import com.vyg.entity.BaseEvent;
import com.vyg.service.BaseEventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/base-events")
public class BaseEventController {

    private final BaseEventService baseEventService;

    public BaseEventController(BaseEventService baseEventService) {
        this.baseEventService = baseEventService;
    }

    /**
     * Create multiple Base Events
     */
    @PostMapping("/create")
    public ResponseEntity<List<BaseEvent>> createBaseEvents(@RequestBody List<BaseEventRequest> baseEventRequests) {
        return ResponseEntity.ok(baseEventService.createBaseEvents(baseEventRequests));
    }

    /**
     * Get all active Base Events
     */
    @GetMapping("/all")
    public ResponseEntity<List<BaseEvent>> getAllActiveBaseEvents() {
        return ResponseEntity.ok(baseEventService.getAllActiveBaseEvents());
    }

    /**
     * Soft delete (disable) a Base Event
     */
    @DeleteMapping("/disable/{eventId}")
    public ResponseEntity<String> disableBaseEvent(@PathVariable Long eventId) {
        baseEventService.disableBaseEvent(eventId);
        return ResponseEntity.ok("Event disabled successfully!");
    }
}

