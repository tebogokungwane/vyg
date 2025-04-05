package com.vyg.repository;

import com.vyg.entity.CapturedPoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CapturedPointRepository extends JpaRepository<CapturedPoint,Long> {

    boolean existsByNation_IdAndBaseEvent_IdAndWeekNumberAndYear(int nationId, Long eventId, int weekNumber, int year
    );

}
