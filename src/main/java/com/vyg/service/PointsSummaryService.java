package com.vyg.service;

import com.vyg.dto.NationPointsSummaryDTO;
import com.vyg.repository.CapturedPointRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PointsSummaryService {

    private final CapturedPointRepository capturedPointRepository;

    public PointsSummaryService(CapturedPointRepository capturedPointRepository) {
        this.capturedPointRepository = capturedPointRepository;
    }

    public List<NationPointsSummaryDTO> getSummary(Long addressId) {
        return capturedPointRepository.getSummaryByAddress(addressId);
    }
}

