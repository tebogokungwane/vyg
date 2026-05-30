package com.vyg.repository;

import com.vyg.entity.Branding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BrandingRepository extends JpaRepository<Branding, Long> {
    Optional<Branding> findByType(String type);
}
