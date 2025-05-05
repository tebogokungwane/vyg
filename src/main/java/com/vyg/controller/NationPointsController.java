package com.vyg.controller;


import com.vyg.entity.NationPoints;
import com.vyg.service.NationPointsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.vyg.dto.NationPointsRequest;

@RestController
@RequestMapping("/api/nation-points")
public class NationPointsController {

    private final NationPointsService nationPointsService;

    public NationPointsController(NationPointsService nationPointsService) {
        this.nationPointsService = nationPointsService;
    }

    /**
     * Add Points to a Nation for an Event
     */
    @PostMapping("/addPoints")
    public ResponseEntity<NationPoints> addPoints(@RequestBody NationPointsRequest request) {
        return ResponseEntity.ok(nationPointsService.addPoints(request));
    }
}
