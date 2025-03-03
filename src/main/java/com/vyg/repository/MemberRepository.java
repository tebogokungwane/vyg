package com.vyg.repository;

import com.vyg.entity.Members;
import com.vyg.enumerator.Nation;
import com.vyg.enumerator.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Members, Long> {
    List<Members> findByMentorId(Long mentorId);

    //List<Members> getMenteesByMentor(Long mentorId);

    List<Members> findByRole(Role role);


    List<Members> findByRoleAndProvince_NameAndRegion_Name(Role role, String provinceName, String regionName);


}
