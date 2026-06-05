package com.vyg.controller;

import com.vyg.dto.MoveMemberRequest;
import com.vyg.dto.ProjectDTO;
import com.vyg.entity.Members;
import com.vyg.entity.Project;
import com.vyg.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping
    public ResponseEntity<List<ProjectDTO>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Project> createProject(
            @RequestParam("name") String name,
            @RequestParam(value = "image", required = false) MultipartFile image) {
        try {
            Project project = projectService.createProject(name, image);
            return ResponseEntity.status(HttpStatus.CREATED).body(project);
        } catch (IOException e) {
            log.error("Error creating project", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Project> updateProject(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam(value = "image", required = false) MultipartFile image) {
        try {
            Project project = projectService.updateProject(id, name, image);
            return ResponseEntity.ok(project);
        } catch (IOException e) {
            log.error("Error updating project", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getProjectImage(@PathVariable Long id) {
        Project project = projectService.getProjectById(id);

        if (project.getImageData() == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(project.getImageType()))
                .body(project.getImageData());
    }

    @GetMapping("/{id}/members")
    public ResponseEntity<List<Members>> getProjectMembers(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getMembersByProject(id));
    }

    @PutMapping("/move-member")
    public ResponseEntity<Void> moveMember(@RequestBody MoveMemberRequest request) {
        projectService.moveMember(request.getMemberId(), request.getFromProjectId(), request.getToProjectId());
        return ResponseEntity.ok().build();
    }
}
