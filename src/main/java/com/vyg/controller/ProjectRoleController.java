package com.vyg.controller;

import com.vyg.dto.AssignRoleRequest;
import com.vyg.entity.ProjectMemberRole;
import com.vyg.service.ProjectRoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectRoleController {

    private final ProjectRoleService projectRoleService;

    public ProjectRoleController(ProjectRoleService projectRoleService) {
        this.projectRoleService = projectRoleService;
    }

    @GetMapping("/{projectId}/roles")
    public ResponseEntity<List<ProjectMemberRole>> getRoles(@PathVariable Long projectId) {
        return ResponseEntity.ok(projectRoleService.getRolesByProject(projectId));
    }

    @PostMapping("/{projectId}/assign-role")
    public ResponseEntity<ProjectMemberRole> assignRole(@PathVariable Long projectId,
                                                        @RequestBody AssignRoleRequest request) {
        return ResponseEntity.ok(projectRoleService.assignRole(projectId, request));
    }

    @PutMapping("/{projectId}/update-role/{roleId}")
    public ResponseEntity<ProjectMemberRole> updateRole(@PathVariable Long projectId,
                                                        @PathVariable Long roleId,
                                                        @RequestBody AssignRoleRequest request) {
        return ResponseEntity.ok(projectRoleService.updateRole(projectId, roleId, request));
    }

    @DeleteMapping("/{projectId}/remove-role/{roleId}")
    public ResponseEntity<Void> removeRole(@PathVariable Long projectId, @PathVariable Long roleId) {
        projectRoleService.removeRole(roleId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/hierarchy")
    public ResponseEntity<List<ProjectMemberRole>> getHierarchy() {
        return ResponseEntity.ok(projectRoleService.getFullHierarchy());
    }
}
