package com.vyg.repository;

import com.vyg.entity.Members;
import com.vyg.entity.Nations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NationsRepository extends JpaRepository<Nations, Long> {

    List<Nations> findByAddressId(Long addressId);

}
