package com.vyg.repository;

import com.vyg.entity.VygSchool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VygSchoolRepository extends JpaRepository<VygSchool, String> {

    List<VygSchool> findByAddress_Id(Long addressId);

    List<VygSchool> findByAddressIsNull();
}
