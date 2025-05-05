package com.vyg.service;

import com.vyg.dto.DashboardSummaryDTO;
import com.vyg.entity.Members;
import com.vyg.entity.Nations;
import com.vyg.enumerator.Role;
import com.vyg.repository.MemberRepository;
import com.vyg.repository.NationsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor

public class DashboardService {

    private final MemberRepository membersRepository;
    private final NationsRepository nationsRepository;

    @Transactional
    public DashboardSummaryDTO getSummaryByAddress(Long addressId) {
        List<Members> members = membersRepository.findByAddressId(addressId);
        List<Nations> nations = nationsRepository.findAll();

        long totalMembers = members.size();
        long totalMentors = members.stream().filter(m -> m.getRole().name().equalsIgnoreCase("MENTOR")).count();
        long totalSecretaries = members.stream().filter(m -> m.getRole().name().equalsIgnoreCase("SECRETARY")).count();
        int totalNations = nations.size();
        int totalPoints = nations.stream().mapToInt(Nations::getTotalPoints).sum();

        String topNation = nations.stream()
                .max(Comparator.comparingInt(Nations::getTotalPoints))
                .map(Nations::getNation)
                .orElse("No data");

        return new DashboardSummaryDTO(
                totalMembers,
                totalMentors,
                totalSecretaries,
                totalNations,
                totalPoints,
                topNation
        );
    }
}

