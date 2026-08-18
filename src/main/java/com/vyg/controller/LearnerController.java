package com.vyg.controller;

import com.vyg.dto.LearnerDTO;
import com.vyg.dto.RegisterLearnerRequest;
import com.vyg.dto.SchoolWithLearnersDTO;
import com.vyg.entity.Members;
import com.vyg.repository.MemberRepository;
import com.vyg.service.LearnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/learners")
@RequiredArgsConstructor
public class LearnerController {

    private final LearnerService learnerService;
    private final MemberRepository memberRepository;

    /**
     * POST /api/learners/register
     * Registers a new learner under a school identified by schoolCode.
     */
    @PostMapping("/register")
    public ResponseEntity<LearnerDTO> registerLearner(@RequestBody RegisterLearnerRequest request) {
        LearnerDTO created = learnerService.registerLearner(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * POST /api/learners
     * Alternative endpoint — same logic, matches frontend URL.
     */
    @PostMapping
    public ResponseEntity<LearnerDTO> registerLearnerAlt(@RequestBody RegisterLearnerRequest request) {
        LearnerDTO created = learnerService.registerLearner(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * GET /api/learners/schools-with-learners
     * Returns schools with their learners for the logged-in user's branch address.
     */
    @GetMapping("/schools-with-learners")
    public ResponseEntity<List<SchoolWithLearnersDTO>> getSchoolsForLoggedInUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Members member = memberRepository.findByEmail(email).orElse(null);

        if (member == null || member.getAddress() == null) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        return ResponseEntity.ok(learnerService.getSchoolsWithLearnersByAddress(member.getAddress().getId()));
    }

    /**
     * GET /api/learners/schools-with-learners/{addressId}
     * Returns schools with their learners filtered by address (branch).
     */
    @GetMapping("/schools-with-learners/{addressId}")
    public ResponseEntity<List<SchoolWithLearnersDTO>> getSchoolsWithLearnersByAddress(@PathVariable Long addressId) {
        return ResponseEntity.ok(learnerService.getSchoolsWithLearnersByAddress(addressId));
    }

    /**
     * GET /api/learners/school/{schoolId}
     * Returns a specific school with its learners.
     */
    @GetMapping("/school/{schoolId}")
    public ResponseEntity<SchoolWithLearnersDTO> getSchoolWithLearners(@PathVariable String schoolId) {
        return ResponseEntity.ok(learnerService.getSchoolWithLearners(schoolId));
    }
}
