package com.vyg.repository;

import com.vyg.dto.NationMemberStatsDTO;
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

    Optional<Members> findByEmail(String email);

    List<Members> findByRoleAndAddressId(Role role, Long addressId);

    Page<Members> findAllByAddressId(Long addressId, Pageable pageable);

    List<Nations> findAllByAddress_Id(Long addressId);

    @Query("""
        SELECT new com.vyg.dto.NationMemberStatsDTO(
            n.id,
            SUM(CASE WHEN m.role = 'MEMBER' THEN 1 ELSE 0 END),
            SUM(CASE WHEN m.role = 'MENTOR' THEN 1 ELSE 0 END),
            SUM(CASE WHEN m.role = 'SECRETARY' THEN 1 ELSE 0 END)
        )
        FROM Members m
        JOIN m.nation n
        WHERE m.address.id = :addressId
        GROUP BY n.id
    """)
    List<NationMemberStatsDTO> getNationMemberStats(@Param("addressId") Long addressId);



    // 1️⃣ Unassigned mentees
    @Query("""
        SELECT m
        FROM Members m
        WHERE m.address.id = :addressId
          AND m.mentor IS NULL
          AND m.role = 'MEMBER'
    """)
    List<Members> findUnassignedMenteesByAddress(
            @Param("addressId") Long addressId
    );

    // 2️⃣ Mentors with mentee count (paginated)
    @Query("""
        SELECT m.id,
               m.name,
               m.surname,
               m.cellNumber,
               m.nation,
               a.branch,
               COUNT(mentee)
        FROM Members m
        LEFT JOIN m.mentees mentee
        JOIN m.address a
        WHERE m.role = 'MENTOR'
          AND a.id = :addressId
        GROUP BY m.id, m.name, m.surname, m.cellNumber, m.nation, a.branch
    """)
    Page<Object[]> findMentorsWithMenteeCount(
            @Param("addressId") Long addressId,
            Pageable pageable
    );

}
