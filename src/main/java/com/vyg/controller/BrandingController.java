package com.vyg.controller;

import com.vyg.entity.Branding;
import com.vyg.service.BrandingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/branding")
@RequiredArgsConstructor
public class BrandingController {

    private final BrandingService brandingService;

    @PostMapping(value = "/{type}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> upload(
            @PathVariable String type,
            @RequestParam("file") MultipartFile file
    ) {
        Branding saved = brandingService.upload(type, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved.getId());
    }

    @GetMapping("/{type}")
    public ResponseEntity<?> get(@PathVariable String type) {
        try {
            Branding branding = brandingService.getByType(type);
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(branding.getImageType()))
                    .body(branding.getImageData());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
