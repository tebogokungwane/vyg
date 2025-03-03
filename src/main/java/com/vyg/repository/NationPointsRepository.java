package com.vyg.repository;

import com.vyg.entity.NationPoints;
import com.vyg.enumerator.Nation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NationPointsRepository extends JpaRepository<NationPoints, Long> {
    List<NationPoints> findByNation(Nation nation);
    List<NationPoints> findByProvince(String province);
    List<NationPoints> findByRegion(String region);
}
