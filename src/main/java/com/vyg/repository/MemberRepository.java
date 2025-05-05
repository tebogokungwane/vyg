package com.vyg.repository;

import com.vyg.entity.Members;
import com.vyg.entity.Nations;
import com.vyg.enumerator.Nation;
import com.vyg.enumerator.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Members, Long> {

    List<Members> findByMentorId(Long mentorId);

    List<Members> findByAddressId(Long addressId);

    List<Members> findByRole(Role role);

    Optional<Members> findByEmailAndPassword(String email, String password);

    List<Members> findByRoleAndAddressId(Role role, Long addressId);

    Page<Members> findAllByAddressId(Long addressId, Pageable pageable);

    List<Nations> findAllByAddress_Id(Long addressId);


}
