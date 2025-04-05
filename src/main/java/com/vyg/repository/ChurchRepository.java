package com.vyg.repository;

import com.vyg.entity.Church;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChurchRepository extends JpaRepository<Church,Long> {
}
