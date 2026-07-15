package com.vyg.controller;

import com.vyg.dto.PagedResponse;
import com.vyg.dto.SchoolInstitutionRequestDTO;
import com.vyg.entity.SchoolInstitution;
import com.vyg.service.SchoolInstitutionService;
//import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/school-institutions")
@RequiredArgsConstructor
public class SchoolInstitutionController {

    private final SchoolInstitutionService service;

    // ==================== READ (Paginated) ====================

    /**
     * GET /api/school-institutions?page=0&size=20&sortBy=officialInstitutionName&direction=asc
     */
    @GetMapping
    public ResponseEntity<PagedResponse<SchoolInstitution>> getAllPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "officialInstitutionName") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        return ResponseEntity.ok(service.findAllPaged(page, size, sortBy, direction));
    }


    /**
     * GET /api/school-institutions/all — returns all records (no pagination)
     */
    @GetMapping("/all")
    public ResponseEntity<List<SchoolInstitution>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SchoolInstitution> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/nat-emis/{natEmis}")
    public ResponseEntity<SchoolInstitution> getByNatEmis(@PathVariable Long natEmis) {
        return service.findByNatEmis(natEmis)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ==================== SEARCH ====================

    /**
     * GET /api/school-institutions/search?name=example&page=0&size=20
     */
    @GetMapping("/search")
    public ResponseEntity<PagedResponse<SchoolInstitution>> searchByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(service.searchByName(name, page, size));
    }

    /**
     * GET /api/school-institutions/search/global?query=gauteng&page=0&size=20
     * Searches across name, province, district, and town
     */
    @GetMapping("/search/global")
    public ResponseEntity<PagedResponse<SchoolInstitution>> searchGlobal(
            @RequestParam String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(service.searchGlobal(query, page, size));
    }

    // ==================== FILTER (Paginated) ====================

    @GetMapping("/province/{province}")
    public ResponseEntity<PagedResponse<SchoolInstitution>> getByProvince(
            @PathVariable String province,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(service.findByProvince(province, page, size));
    }

    @GetMapping("/district/{district}")
    public ResponseEntity<PagedResponse<SchoolInstitution>> getByDistrict(
            @PathVariable String district,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(service.findByDistrict(district, page, size));
    }

    @GetMapping("/phase/{phase}")
    public ResponseEntity<PagedResponse<SchoolInstitution>> getByPhase(
            @PathVariable String phase,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return ResponseEntity.ok(service.findByPhase(phase, page, size));
    }

    @GetMapping("/sector/{sector}")
    public ResponseEntity<List<SchoolInstitution>> getBySector(@PathVariable String sector) {
        return ResponseEntity.ok(service.findBySector(sector));
    }

    @GetMapping("/urban-rural/{type}")
    public ResponseEntity<List<SchoolInstitution>> getByUrbanRural(@PathVariable String type) {
        return ResponseEntity.ok(service.findByUrbanRural(type));
    }

    @GetMapping("/town/{townCity}")
    public ResponseEntity<List<SchoolInstitution>> getByTownCity(@PathVariable String townCity) {
        return ResponseEntity.ok(service.findByTownCity(townCity));
    }

    @GetMapping("/nearby")
    public ResponseEntity<List<SchoolInstitution>> findNearby(
            @RequestParam Double lat,
            @RequestParam Double lng,
            @RequestParam(defaultValue = "10") Double radiusKm) {
        return ResponseEntity.ok(service.findNearby(lat, lng, radiusKm));
    }

    // ==================== CREATE ====================

    /**
     * POST /api/school-institutions
     */
    @PostMapping
    public ResponseEntity<SchoolInstitution> create(@Valid @RequestBody SchoolInstitutionRequestDTO dto) {
        SchoolInstitution created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // ==================== UPDATE ====================

    /**
     * PUT /api/school-institutions/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<SchoolInstitution> update(
            @PathVariable Long id,
            @Valid @RequestBody SchoolInstitutionRequestDTO dto) {
        SchoolInstitution updated = service.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    // ==================== DELETE ====================

    /**
     * DELETE /api/school-institutions/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * DELETE /api/school-institutions/nat-emis/{natEmis}
     */
    @DeleteMapping("/nat-emis/{natEmis}")
    public ResponseEntity<Void> deleteByNatEmis(@PathVariable Long natEmis) {
        service.deleteByNatEmis(natEmis);
        return ResponseEntity.noContent().build();
    }

    // ==================== STATS & FILTER OPTIONS ====================

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        Map<String, Object> stats = Map.of(
                "totalSchools", service.totalCount(),
                "publicSchools", service.countBySector("PUBLIC"),
                "independentSchools", service.countBySector("INDEPENDENT")
        );
        return ResponseEntity.ok(stats);
    }



    /**
     * GET /api/school-institutions/filters
     * Returns distinct values for dropdowns/filters in React UI
     */
    @GetMapping("/filters")
    public ResponseEntity<Map<String, List<String>>> getFilterOptions() {
        Map<String, List<String>> filters = Map.of(
                "provinces", service.getDistinctProvinces(),
                "districts", service.getDistinctDistricts(),
                "phases", service.getDistinctPhases(),
                "sectors", service.getDistinctSectors(),
                "quintiles", service.getDistinctQuintiles()
        );
        return ResponseEntity.ok(filters);
    }
}
