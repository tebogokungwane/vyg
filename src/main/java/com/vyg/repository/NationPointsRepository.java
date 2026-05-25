package com.vyg.repository;

//import com.vyg.entity.CapturedPoint;
import com.vyg.entity.NationPoints;
import com.vyg.enumerator.ApprovalStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NationPointsRepository extends JpaRepository<NationPoints, Long> {

    // ================= YEARLY =================
    @Query(value = """
        SELECT np.nation, SUM(np.points_earned)
        FROM nation_points np
        JOIN base_events e ON e.id = np.event_id
        WHERE UPPER(np.province) = UPPER(:province)
          AND UPPER(np.region) = UPPER(:region)
          AND EXTRACT(YEAR FROM e.event_date) = :year
        GROUP BY np.nation
        ORDER BY SUM(np.points_earned) DESC
        """, nativeQuery = true)
    List<Object[]> findYearlyPerformance(
            @Param("province") String province,
            @Param("region") String region,
            @Param("year") int year
    );

    // ================= MONTHLY =================
    @Query(value = """
        SELECT np.nation, SUM(np.points_earned)
        FROM nation_points np
        JOIN base_events e ON e.id = np.event_id
        WHERE UPPER(np.province) = UPPER(:province)
          AND UPPER(np.region) = UPPER(:region)
          AND EXTRACT(MONTH FROM e.event_date) = :month
          AND EXTRACT(YEAR FROM e.event_date) = :year
        GROUP BY np.nation
        ORDER BY SUM(np.points_earned) DESC
        """, nativeQuery = true)
    List<Object[]> findMonthlyPerformance(
            @Param("province") String province,
            @Param("region") String region,
            @Param("month") int month,
            @Param("year") int year
    );

    // ================= WEEKLY =================
    @Query(value = """
        SELECT np.nation, SUM(np.points_earned)
        FROM nation_points np
        JOIN base_events e ON e.id = np.event_id
        WHERE UPPER(np.province) = UPPER(:province)
          AND UPPER(np.region) = UPPER(:region)
          AND np.week_number = :week
          AND EXTRACT(YEAR FROM e.event_date) = :year
        GROUP BY np.nation
        ORDER BY SUM(np.points_earned) DESC
        """, nativeQuery = true)
    List<Object[]> findWeeklyPerformance(
            @Param("province") String province,
            @Param("region") String region,
            @Param("week") int week,
            @Param("year") int year
    );


}
