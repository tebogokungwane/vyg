package com.vyg.controller;

import com.vyg.model.BranchRequest;
import com.vyg.entity.Branch;
import com.vyg.service.BranchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/branches")
public class BranchController {

    private final BranchService branchService;

    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    @PostMapping("/add")
    public ResponseEntity<Branch> addBranch(@RequestBody BranchRequest request) {
        return ResponseEntity.ok(branchService.createBranch(request));
    }


    @GetMapping("/getBranches")
    public ResponseEntity<List<Branch>> getBranches() {
        return ResponseEntity.ok(branchService.getBranches());  // ✅ Corrected return type
    }

}
