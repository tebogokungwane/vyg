package com.vyg.repository;

import com.vyg.entity.ProjectMemberRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectMemberRoleRepository extends JpaRepository<ProjectMemberRole, Long> {
    List<ProjectMemberRole> findByProjectId(Long projectId);
}
