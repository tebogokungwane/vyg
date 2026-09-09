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
     * If no auth or user has no address, returns all schools.
     */
    @GetMapping("/schools-with-learners")
    public ResponseEntity<List<SchoolWithLearnersDTO>> getSchoolsForLoggedInUser() {
        try {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            if (email != null && !email.equals("anonymousUser")) {
                Members member = memberRepository.findByEmail(email).orElse(null);
                // Authenticated user WITH an address: scope strictly to their own
                // branch/address. Schools are branch-specific (e.g. the seeded
                // schools belong to Park Station), so a user from another branch
                // must NOT see them — return only their address's schools, even
                // if that list is empty.
                if (member != null && member.getAddress() != null) {
                    List<SchoolWithLearnersDTO> result =
                            learnerService.getSchoolsWithLearnersByAddress(member.getAddress().getId());
                    return ResponseEntity.ok(result);
                }
            }
        } catch (Exception ignored) {
        }
        // Fallback (unauthenticated / no address on the account): return all schools.
        return ResponseEntity.ok(learnerService.getAllSchoolsWithLearners());
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
