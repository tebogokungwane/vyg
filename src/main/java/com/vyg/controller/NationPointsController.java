package com.vyg.controller;


import com.vyg.entity.NationPoints;
import com.vyg.enumerator.Nation;
import com.vyg.service.NationPointsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.vyg.model.NationPointsRequest;
import java.util.List;

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
    @PostMapping("/add")
    public ResponseEntity<NationPoints> addPoints(@RequestBody NationPointsRequest request) {
        return ResponseEntity.ok(nationPointsService.addPoints(request));
    }


    /**
     * Get Points Earned by a Nation
     */
    @GetMapping("/{nation}")
    public ResponseEntity<List<NationPoints>> getNationPoints(@PathVariable String nation) {
        Nation nationEnum = Nation.valueOf(nation.toUpperCase());
        return ResponseEntity.ok(nationPointsService.getNationPoints(nationEnum));
    }
}
