package com.vyg.controller;

import com.vyg.dto.MoveMemberRequest;
import com.vyg.dto.ProjectDTO;
import com.vyg.entity.Members;
import com.vyg.entity.Project;
import com.vyg.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    // GET /api/projects
    @GetMapping
    public ResponseEntity<List<ProjectDTO>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    // GET /api/projects/{id}/members
    @GetMapping("/{id}/members")
    public ResponseEntity<List<Members>> getProjectMembers(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getMembersByProject(id));
    }

    // GET /api/projects/{id}/image
    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getProjectImage(@PathVariable Long id) {
        byte[] image = projectService.getProjectImage(id);
        String type = projectService.getProjectImageType(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, type != null ? type : "image/png")
                .body(image);
    }

    // POST /api/projects
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Project> createProject(
            @RequestParam("name") String name,
            @RequestParam(value = "image", required = false) MultipartFile image
    ) throws IOException {
        return ResponseEntity.ok(projectService.createProject(name, image));
    }

    // PUT /api/projects/{id}
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Project> updateProject(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam(value = "image", required = false) MultipartFile image
    ) throws IOException {
        return ResponseEntity.ok(projectService.updateProject(id, name, image));
    }

    // DELETE /api/projects/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    // PUT /api/projects/{id}/assign-member/{memberId}
    @PutMapping("/{id}/assign-member/{memberId}")
    public ResponseEntity<String> assignMember(@PathVariable Long id, @PathVariable Long memberId) {
        projectService.assignMember(id, memberId);
        return ResponseEntity.ok("Member assigned successfully");
    }

    // POST /api/projects/{projectId}/add-member/{memberId}
    @PostMapping("/{projectId}/add-member/{memberId}")
    public ResponseEntity<String> addMember(@PathVariable Long projectId, @PathVariable Long memberId) {
        String message = projectService.addMember(projectId, memberId);
        return ResponseEntity.ok(message);
    }

    // GET /api/projects/unassigned-members/address/{addressId}
    @GetMapping("/unassigned-members/address/{addressId}")
    public ResponseEntity<List<Members>> getUnassignedMembers(@PathVariable Long addressId) {
        return ResponseEntity.ok(projectService.getUnassignedMembersByAddress(addressId));
    }

    // PUT /api/projects/move-member
    @PutMapping("/move-member")
    public ResponseEntity<String> moveMember(@RequestBody MoveMemberRequest request) {
        projectService.moveMember(
                request.getMemberId(),
                request.getFromProjectId(),
                request.getToProjectId()
        );
        return ResponseEntity.ok("Member moved successfully");
    }
}
