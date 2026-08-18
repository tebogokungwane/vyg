package com.vyg.repository;

import com.vyg.entity.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {

    Optional<Advertisement> findByActiveTrue();

    @Modifying
    @Query("UPDATE Advertisement a SET a.active = false WHERE a.active = true")
    void deactivateAll();
}
