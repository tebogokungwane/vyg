package com.vyg.controller;

import com.vyg.dto.NationRequest;
import com.vyg.dto.NationSettingsDTO;
import com.vyg.entity.Nations;
import com.vyg.mapper.NationSettingsMapper;
import com.vyg.service.NationsSettingsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/nations")
@RequiredArgsConstructor
public class NationsSettingsController {

    private final NationsSettingsService nationsSettingsService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createNation(
            @RequestParam("nation") String nation,
            @RequestParam("imageFile") MultipartFile imageFile
    ) {
        System.out.println("🔥 SUCCESS: Reached Controller!");
        System.out.println("Nation Name: " + nation);
        System.out.println("File Name: " + imageFile.getOriginalFilename());

        try {
            Nations saved = nationsSettingsService.create(nation, imageFile);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping
    public List<NationSettingsDTO> getAll() {
        return nationsSettingsService.getAll()
                .stream()
                .map(NationSettingsMapper::toDto)
                .toList();
    }

    @GetMapping("/{id}")
    public NationSettingsDTO getById(@PathVariable Long id) {
        return NationSettingsMapper.toDto(nationsSettingsService.getById(id));
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<?> getImage(@PathVariable Long id) {
        try {
            Nations nation = nationsSettingsService.getById(id);

            // Check if nation or image data is missing
            if (nation == null || nation.getImageData() == null || nation.getImageType() == null) {
                log.warn("LOG: Image missing for nation ID: {}", id);
                return ResponseEntity.notFound().build(); // Returns 404 (Safe for frontend)
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(nation.getImageType()))
                    .body(nation.getImageData());

        } catch (Exception e) {
            log.error("LOG ERROR: Failed to stream image for ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestParam(value = "nation", required = false) String nation,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile
    ) {
        if (nation != null) {
            nationsSettingsService.updateName(id, nation);
        }
        if (imageFile != null && !imageFile.isEmpty()) {
            nationsSettingsService.updateImage(id, imageFile);
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/name")
    public ResponseEntity<?> updateName(
            @PathVariable Long id,
            @RequestBody NationRequest request
    ) {
        nationsSettingsService.updateName(id, request.getNation());
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateImage(
            @PathVariable Long id,
            @RequestParam("imageFile") MultipartFile imageFile
    ) {
        nationsSettingsService.updateImage(id, imageFile);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        nationsSettingsService.delete(id);
        return ResponseEntity.ok().build();
    }
}
