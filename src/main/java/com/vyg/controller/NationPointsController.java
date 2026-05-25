package com.vyg.controller;


import com.vyg.entity.NationPoints;
import com.vyg.service.NationPointsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.vyg.dto.NationPointsRequest;

@RestController
@RequestMapping("/api/points") // changed from /api/nation-points
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

    @GetMapping("/nation-stats/address/{addressId}")
    public ResponseEntity<?> getNationStats(@PathVariable Long addressId) {
        // Default to yearly view for charts
        int currentYear = java.time.LocalDate.now().getYear();
        return ResponseEntity.ok(
                nationPointsService.getTopPerformance(addressId, "yearly", null, null, currentYear)
        );
    }

    @GetMapping("/top-performance/address/{addressId}")
    public ResponseEntity<?> getTopPerformanceByAddress(
            @PathVariable Long addressId,
            @RequestParam String viewMode,
            @RequestParam(required = false) Integer week,
            @RequestParam(required = false) Integer month,
            @RequestParam Integer year
    ) {
        return ResponseEntity.ok(
                nationPointsService.getTopPerformance(addressId, viewMode, week, month, year)
        );
    }


        @PutMapping("/approve/{id}")
        public ResponseEntity<?> approvePoints(
                @PathVariable Long id,
                @RequestParam String approvedBy
        ) {
            nationPointsService.approve(id, approvedBy);
            return ResponseEntity.ok("Points approved successfully");
        }



}
