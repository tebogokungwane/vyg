package com.vyg.repository;

import com.vyg.entity.Province;
import com.vyg.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface RegionRepository extends JpaRepository<Region,Long> {

    Optional<Region> findByNameAndProvince(String name, Province province);
}
