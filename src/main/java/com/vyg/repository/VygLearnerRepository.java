package com.vyg.repository;

import com.vyg.entity.VygLearner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VygLearnerRepository extends JpaRepository<VygLearner, String> {

    List<VygLearner> findBySchool_SchoolId(String schoolId);
}
