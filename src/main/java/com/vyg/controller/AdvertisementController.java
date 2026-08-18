package com.vyg.controller;

import com.vyg.dto.AdvertisementDTO;
import com.vyg.entity.Advertisement;
import com.vyg.service.AdvertisementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/advertisements")
@RequiredArgsConstructor
public class AdvertisementController {

    private final AdvertisementService advertisementService;

    /**
     * GET /api/advertisements
     * List all advertisements (metadata only, no binary).
     */
    @GetMapping
    public ResponseEntity<List<AdvertisementDTO>> listAll() {
        return ResponseEntity.ok(advertisementService.listAll());
    }

    /**
     * GET /api/advertisements/active
     * Returns the image binary of the currently active ad.
     */
    @GetMapping("/active")
    public ResponseEntity<byte[]> getActiveImage() {
        return advertisementService.getActive()
                .map(ad -> ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(ad.getContentType()))
                        .body(ad.getData()))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET /api/advertisements/{id}/image
     * Returns the image binary for a specific ad.
     */
    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getImageById(@PathVariable Long id) {
        return advertisementService.getById(id)
                .map(ad -> ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(ad.getContentType()))
                        .body(ad.getData()))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /api/advertisements/upload
     * Upload a new advertisement image (multipart form-data, field: "file").
     */
    @PostMapping("/upload")
    public ResponseEntity<AdvertisementDTO> upload(@RequestParam("file") MultipartFile file) throws IOException {
        Advertisement saved = advertisementService.upload(file);
        AdvertisementDTO dto = AdvertisementDTO.builder()
                .id(saved.getId())
                .active(saved.getActive())
                .filename(saved.getFilename())
                .createdAt(saved.getCreatedAt())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    /**
     * PUT /api/advertisements/{id}/activate
     * Sets this ad as the active one (deactivates all others).
     */
    @PutMapping("/{id}/activate")
    public ResponseEntity<Void> activate(@PathVariable Long id) {
        advertisementService.activate(id);
        return ResponseEntity.ok().build();
    }

    /**
     * DELETE /api/advertisements/{id}
     * Removes the ad record and its data.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        advertisementService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
