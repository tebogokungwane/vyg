package com.vyg.repository;

import com.vyg.entity.PointAdjustment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointAdjustmentRepository extends JpaRepository<PointAdjustment, Long> {
}
