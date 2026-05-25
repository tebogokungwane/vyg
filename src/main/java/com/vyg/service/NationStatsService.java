package com.vyg.service;

import com.vyg.dto.NationMemberStatsDTO;
import com.vyg.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NationStatsService {

    private final MemberRepository membersRepository;

    public NationStatsService(MemberRepository membersRepository) {
        this.membersRepository = membersRepository;
    }

    public List<NationMemberStatsDTO> getStatsByAddress(Long addressId) {
        return membersRepository.getNationMemberStats(addressId);
    }
}

