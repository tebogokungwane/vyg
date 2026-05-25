package com.vyg.controller;

import com.vyg.dto.NationMemberStatsDTO;
import com.vyg.service.NationStatsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/nation-stats")
public class NationStatsController {

    private final NationStatsService nationStatsService;

    public NationStatsController(NationStatsService nationStatsService) {
        this.nationStatsService = nationStatsService;
    }

    @GetMapping("/member-stats/{addressId}")
    public ResponseEntity<List<NationMemberStatsDTO>> getMemberStats(
            @PathVariable Long addressId) {

        return ResponseEntity.ok(
                nationStatsService.getStatsByAddress(addressId)
        );
    }
}

