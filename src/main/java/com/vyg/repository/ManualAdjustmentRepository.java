package com.vyg.repository;
import com.vyg.entity.ManualAdjustment;
import com.vyg.enumerator.ApprovalStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManualAdjustmentRepository extends JpaRepository<ManualAdjustment, Long> {
//    List<ManualAdjustment> findByAddress_IdAndApprovalStatus(
//            Long addressId,
//            ApprovalStatus approvalStatus
//    );

    List<ManualAdjustment> findByAddress_IdAndApprovalStatus(Long addressId, ApprovalStatus approvalStatus);


}
