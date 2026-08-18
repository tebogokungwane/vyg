package com.vyg.service;

import com.vyg.dto.PagedResponse;
import com.vyg.dto.SchoolInstitutionRequestDTO;
import com.vyg.entity.SchoolInstitution;
import com.vyg.repository.SchoolInstitutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SchoolInstitutionServiceImpl implements SchoolInstitutionService {

    private final SchoolInstitutionRepository repository;

    @Override
    public PagedResponse<SchoolInstitution> findAllPaged(int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<SchoolInstitution> result = repository.findAll(pageable);
        return toPagedResponse(result);
    }

    @Override
    public List<SchoolInstitution> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<SchoolInstitution> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<SchoolInstitution> findByNatEmis(Long natEmis) {
        return repository.findByNatEmis(natEmis);
    }

    @Override
    public PagedResponse<SchoolInstitution> searchByName(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<SchoolInstitution> result = repository.findByOfficialInstitutionNameContainingIgnoreCase(name, pageable);
        return toPagedResponse(result);
    }

    @Override
    public PagedResponse<SchoolInstitution> searchGlobal(String query, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<SchoolInstitution> result = repository.searchGlobal(query, pageable);
        return toPagedResponse(result);
    }

    @Override
    public PagedResponse<SchoolInstitution> findByProvince(String province, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<SchoolInstitution> result = repository.findByProvinceIgnoreCase(province, pageable);
        return toPagedResponse(result);
    }

    @Override
    public PagedResponse<SchoolInstitution> findByDistrict(String district, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<SchoolInstitution> result = repository.findByEiDistrictIgnoreCase(district, pageable);
        return toPagedResponse(result);
    }

    @Override
    public PagedResponse<SchoolInstitution> findByPhase(String phase, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<SchoolInstitution> result = repository.findByPhasePedIgnoreCase(phase, pageable);
        return toPagedResponse(result);
    }

    @Override
    public List<SchoolInstitution> findBySector(String sector) {
        return repository.findBySectorIgnoreCase(sector);
    }

    @Override
    public List<SchoolInstitution> findByUrbanRural(String type) {
        return repository.findByUrbanRuralIgnoreCase(type);
    }

    @Override
    public List<SchoolInstitution> findByTownCity(String townCity) {
        return repository.findByTownCityIgnoreCase(townCity);
    }

    @Override
    public List<SchoolInstitution> findNearby(Double lat, Double lng, Double radiusKm) {
        return repository.findNearby(lat, lng, radiusKm);
    }

    @Override
    public SchoolInstitution create(SchoolInstitutionRequestDTO dto) {
        SchoolInstitution entity = mapToEntity(dto, new SchoolInstitution());
        return repository.save(entity);
    }

    @Override
    public SchoolInstitution update(Long id, SchoolInstitutionRequestDTO dto) {
        SchoolInstitution entity = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "School institution not found"));
        mapToEntity(dto, entity);
        return repository.save(entity);
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "School institution not found");
        }
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteByNatEmis(Long natEmis) {
        repository.deleteByNatEmis(natEmis);
    }

    @Override
    public long totalCount() {
        return repository.count();
    }

    @Override
    public long countBySector(String sector) {
        return repository.countBySectorIgnoreCase(sector);
    }

    @Override
    public List<String> getDistinctProvinces() {
        return repository.findDistinctProvinces();
    }

    @Override
    public List<String> getDistinctDistricts() {
        return repository.findDistinctDistricts();
    }

    @Override
    public List<String> getDistinctPhases() {
        return repository.findDistinctPhases();
    }

    @Override
    public List<String> getDistinctSectors() {
        return repository.findDistinctSectors();
    }

    @Override
    public List<String> getDistinctQuintiles() {
        return repository.findDistinctQuintiles();
    }

    private SchoolInstitution mapToEntity(SchoolInstitutionRequestDTO dto, SchoolInstitution entity) {
        entity.setNatEmis(dto.getNatEmis());
        entity.setDataYear(dto.getDataYear());
        entity.setProvince(dto.getProvince());
        entity.setOfficialInstitutionName(dto.getOfficialInstitutionName());
        entity.setStatus(dto.getStatus());
        entity.setSector(dto.getSector());
        entity.setTypeDoe(dto.getTypeDoe());
        entity.setPhasePed(dto.getPhasePed());
        entity.setSpecialisation(dto.getSpecialisation());
        entity.setEiDistrict(dto.getEiDistrict());
        entity.setEiCircuit(dto.getEiCircuit());
        entity.setOwnerLand(dto.getOwnerLand());
        entity.setOwnerBuild(dto.getOwnerBuild());
        entity.setExDept(dto.getExDept());
        entity.setPersalPaypointNo(dto.getPersalPaypointNo());
        entity.setPersalComponentNo(dto.getPersalComponentNo());
        entity.setExamNo(dto.getExamNo());
        entity.setExamCentre(dto.getExamCentre());
        entity.setGisLongitude(dto.getGisLongitude());
        entity.setGisLatitude(dto.getGisLatitude());
        entity.setDistrictMunicipalityName(dto.getDistrictMunicipalityName());
        entity.setLocalMunicipalityName(dto.getLocalMunicipalityName());
        entity.setWardId(dto.getWardId());
        entity.setSpCode(dto.getSpCode());
        entity.setSpName(dto.getSpName());
        entity.setAddressee(dto.getAddressee());
        entity.setTownshipVillage(dto.getTownshipVillage());
        entity.setSuburb(dto.getSuburb());
        entity.setTownCity(dto.getTownCity());
        entity.setStreetAddress(dto.getStreetAddress());
        entity.setPostalAddress(dto.getPostalAddress());
        entity.setEmail(dto.getEmail());
        entity.setTelephone(dto.getTelephone());
        entity.setSection21(dto.getSection21());
        entity.setSection21Function(dto.getSection21Function());
        entity.setQuintile(dto.getQuintile());
        entity.setNas(dto.getNas());
        entity.setNodalArea(dto.getNodalArea());
        entity.setRegistrationDate(dto.getRegistrationDate());
        entity.setNoFeeSchool(dto.getNoFeeSchool());
        entity.setUrbanRural(dto.getUrbanRural());
        entity.setAllocation(dto.getAllocation());
        entity.setDemarcationFrom(dto.getDemarcationFrom());
        entity.setDemarcationTo(dto.getDemarcationTo());
        entity.setOldNatEmis(dto.getOldNatEmis());
        entity.setNewNatEmis(dto.getNewNatEmis());
        entity.setLearners2025(dto.getLearners2025());
        entity.setEducators2025(dto.getEducators2025());
        return entity;
    }

    private <T> PagedResponse<T> toPagedResponse(Page<T> page) {
        return new PagedResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }
}
