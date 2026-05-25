package com.vyg.repository;

import com.vyg.entity.Address;
import com.vyg.enumerator.Branch;
import com.vyg.enumerator.Province;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {


    Optional<Address> findByProvinceAndBranch(Province province, Branch branch);


}
