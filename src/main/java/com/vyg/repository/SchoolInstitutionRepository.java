package com.vyg.repository;

import com.vyg.entity.SchoolInstitution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SchoolInstitutionRepository extends JpaRepository<SchoolInstitution, Long> {

    Optional<SchoolInstitution> findByNatEmis(Long natEmis);

    Page<SchoolInstitution> findByOfficialInstitutionNameContainingIgnoreCase(String name, Pageable pageable);

    @Query("""
        SELECT s FROM SchoolInstitution s
        WHERE LOWER(s.officialInstitutionName) LIKE LOWER(CONCAT('%', :query, '%'))
           OR LOWER(s.province) LIKE LOWER(CONCAT('%', :query, '%'))
           OR LOWER(s.eiDistrict) LIKE LOWER(CONCAT('%', :query, '%'))
           OR LOWER(s.townCity) LIKE LOWER(CONCAT('%', :query, '%'))
    """)
    Page<SchoolInstitution> searchGlobal(@Param("query") String query, Pageable pageable);

    Page<SchoolInstitution> findByProvinceIgnoreCase(String province, Pageable pageable);

    Page<SchoolInstitution> findByEiDistrictIgnoreCase(String district, Pageable pageable);

    Page<SchoolInstitution> findByPhasePedIgnoreCase(String phase, Pageable pageable);

    List<SchoolInstitution> findBySectorIgnoreCase(String sector);

    List<SchoolInstitution> findByUrbanRuralIgnoreCase(String urbanRural);

    List<SchoolInstitution> findByTownCityIgnoreCase(String townCity);

    long countBySectorIgnoreCase(String sector);

    void deleteByNatEmis(Long natEmis);

    @Query("SELECT DISTINCT s.province FROM SchoolInstitution s WHERE s.province IS NOT NULL ORDER BY s.province")
    List<String> findDistinctProvinces();

    @Query("SELECT DISTINCT s.eiDistrict FROM SchoolInstitution s WHERE s.eiDistrict IS NOT NULL ORDER BY s.eiDistrict")
    List<String> findDistinctDistricts();

    @Query("SELECT DISTINCT s.phasePed FROM SchoolInstitution s WHERE s.phasePed IS NOT NULL ORDER BY s.phasePed")
    List<String> findDistinctPhases();

    @Query("SELECT DISTINCT s.sector FROM SchoolInstitution s WHERE s.sector IS NOT NULL ORDER BY s.sector")
    List<String> findDistinctSectors();

    @Query("SELECT DISTINCT s.quintile FROM SchoolInstitution s WHERE s.quintile IS NOT NULL ORDER BY s.quintile")
    List<String> findDistinctQuintiles();

    @Query(value = """
        SELECT * FROM school_institution s
        WHERE s.gis_latitude IS NOT NULL AND s.gis_longitude IS NOT NULL
          AND (6371 * acos(
                cos(radians(:lat)) * cos(radians(s.gis_latitude)) *
                cos(radians(s.gis_longitude) - radians(:lng)) +
                sin(radians(:lat)) * sin(radians(s.gis_latitude))
          )) <= :radiusKm
        ORDER BY (6371 * acos(
                cos(radians(:lat)) * cos(radians(s.gis_latitude)) *
                cos(radians(s.gis_longitude) - radians(:lng)) +
                sin(radians(:lat)) * sin(radians(s.gis_latitude))
        ))
    """, nativeQuery = true)
    List<SchoolInstitution> findNearby(
            @Param("lat") Double lat,
            @Param("lng") Double lng,
            @Param("radiusKm") Double radiusKm
    );
}
