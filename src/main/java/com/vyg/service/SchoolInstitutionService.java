package com.vyg.service;

import com.vyg.dto.PagedResponse;
import com.vyg.dto.SchoolInstitutionRequestDTO;
import com.vyg.entity.SchoolInstitution;

import java.util.List;
import java.util.Optional;

public interface SchoolInstitutionService {

    PagedResponse<SchoolInstitution> findAllPaged(int page, int size, String sortBy, String direction);

    List<SchoolInstitution> findAll();

    Optional<SchoolInstitution> findById(Long id);

    Optional<SchoolInstitution> findByNatEmis(Long natEmis);

    PagedResponse<SchoolInstitution> searchByName(String name, int page, int size);

    PagedResponse<SchoolInstitution> searchGlobal(String query, int page, int size);

    PagedResponse<SchoolInstitution> findByProvince(String province, int page, int size);

    PagedResponse<SchoolInstitution> findByDistrict(String district, int page, int size);

    PagedResponse<SchoolInstitution> findByPhase(String phase, int page, int size);

    List<SchoolInstitution> findBySector(String sector);

    List<SchoolInstitution> findByUrbanRural(String type);

    List<SchoolInstitution> findByTownCity(String townCity);

    List<SchoolInstitution> findNearby(Double lat, Double lng, Double radiusKm);

    SchoolInstitution create(SchoolInstitutionRequestDTO dto);

    SchoolInstitution update(Long id, SchoolInstitutionRequestDTO dto);

    void delete(Long id);

    void deleteByNatEmis(Long natEmis);

    long totalCount();

    long countBySector(String sector);

    List<String> getDistinctProvinces();

    List<String> getDistinctDistricts();

    List<String> getDistinctPhases();

    List<String> getDistinctSectors();

    List<String> getDistinctQuintiles();
}
