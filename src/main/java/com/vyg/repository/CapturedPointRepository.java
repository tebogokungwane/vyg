package com.vyg.repository;

import com.vyg.entity.BaseEvent;
import com.vyg.entity.CapturedPoint;
import com.vyg.dto.NationPointsSummaryDTO;
import com.vyg.entity.Nations;
import com.vyg.enumerator.ApprovalStatus;
import com.vyg.enumerator.CaptureType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CapturedPointRepository extends JpaRepository<CapturedPoint, Long> {

    List<CapturedPoint> findAllByAddress_Id(Long addressId);

    //    @Query("SELECT new com.vyg.dto.NationPointsSummaryDTO(" +
//            "cp.nation.nation, " +
//            "cp.weekNumber, " +
//            "TO_CHAR(cp.dateCaptured, 'Month'), " +  // Use PostgreSQL-compatible month string
//            "cp.year, " +
//            "CAST(SUM(cp.totalPointsEarnedPerWeek) AS int)) " +
//            "FROM CapturedPoint cp " +
//            "WHERE cp.nation.address.id = :addressId " +
//            "GROUP BY cp.nation.nation, cp.weekNumber, TO_CHAR(cp.dateCaptured, 'Month'), cp.year " +
//            "ORDER BY cp.year DESC, cp.weekNumber DESC")
//    List<NationPointsSummaryDTO> getNationPointsSummaryByAddressId(@Param("addressId") Long addressId);
    @Query("""
                SELECT new com.vyg.dto.NationPointsSummaryDTO(
                    cp.nation,
                    cp.baseEvent,
                    cp.numberOfPeople,
                    cp.points,
                    cp.dateCaptured
                )
                FROM CapturedPoint cp
                WHERE cp.address.id = :addressId
                  AND cp.approvalStatus = com.vyg.enumerator.ApprovalStatus.APPROVED
                ORDER BY cp.dateCaptured DESC
            """)
    List<NationPointsSummaryDTO> getSummaryByAddress(@Param("addressId") Long addressId);

    List<CapturedPoint> findByAddress_IdAndApprovalStatus(
            Long addressId,
            ApprovalStatus approvalStatus
    );

//    boolean existsByNation_IdAndBaseEvent_IdAndWeekNumberAndYearAndApprovalStatus(
//            Long nationId,
//            Long baseEventId,
//            int weekNumber,
//            int year,
//            ApprovalStatus approvalStatus
//    );

    boolean existsByNation_IdAndBaseEvent_IdAndWeekNumberAndYear(
            Long nationId,
            Long baseEventId,
            int weekNumber,
            int year
    );

    Optional<CapturedPoint> findByNationAndBaseEventAndWeekNumberAndYearAndApprovalStatus(
            Nations nation,
            BaseEvent baseEvent,
            int weekNumber,
            int year,
            ApprovalStatus approvalStatus
    );

    List<CapturedPoint> findByAddress_IdAndCaptureTypeAndApprovalStatus(
            Long addressId,
            CaptureType captureType,
            ApprovalStatus approvalStatus
    );

    @Query("""
                SELECT new com.vyg.dto.NationPointsSummaryDTO(
                    cp.nation,
                    cp.baseEvent,
                    cp.numberOfPeople,
                    cp.points,
                    cp.dateCaptured
                )
                FROM CapturedPoint cp
                WHERE cp.approvalStatus = com.vyg.enumerator.ApprovalStatus.APPROVED
                ORDER BY cp.dateCaptured DESC
            """)
    List<NationPointsSummaryDTO> getAllApprovedSummaries();





}
