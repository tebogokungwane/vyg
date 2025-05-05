package com.vyg.repository;

import com.vyg.entity.Schools;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolsRepository extends JpaRepository<Schools, Long> {

    List<Schools> findSchoolsByAddress_Id(Long addressId);


}
