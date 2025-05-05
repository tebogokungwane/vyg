package com.vyg.repository;

import com.vyg.entity.CapturedPoint;
import com.vyg.dto.NationPointsSummaryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CapturedPointRepository extends JpaRepository<CapturedPoint,Long> {

    boolean existsByNation_IdAndBaseEvent_IdAndWeekNumberAndYear(int nationId, Long eventId, int weekNumber, int year
    );

    
    List<CapturedPoint> findAllByAddress_Id(Long addressId);

    @Query("SELECT new com.vyg.dto.NationPointsSummaryDTO(" +
            "cp.nation.nation, " +
            "cp.weekNumber, " +
            "TO_CHAR(cp.dateCaptured, 'Month'), " +  // Use PostgreSQL-compatible month string
            "cp.year, " +
            "CAST(SUM(cp.totalPointsEarnedPerWeek) AS int)) " +
            "FROM CapturedPoint cp " +
            "WHERE cp.nation.address.id = :addressId " +
            "GROUP BY cp.nation.nation, cp.weekNumber, TO_CHAR(cp.dateCaptured, 'Month'), cp.year " +
            "ORDER BY cp.year DESC, cp.weekNumber DESC")
    List<NationPointsSummaryDTO> getNationPointsSummaryByAddressId(@Param("addressId") Long addressId);



}
