package com.vyg.repository;

import com.vyg.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("SELECT DISTINCT m.id FROM Project p JOIN p.members m")
    List<Long> findAllAssignedMemberIds();
}