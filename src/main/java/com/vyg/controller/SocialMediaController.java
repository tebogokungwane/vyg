package com.vyg.controller;

import com.vyg.entity.SocialMedia;
import com.vyg.repository.SocialMediaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/social-media")
@RequiredArgsConstructor
public class SocialMediaController {

    private final SocialMediaRepository socialMediaRepository;

    @GetMapping
    public List<SocialMedia> getAll() {
        return socialMediaRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<SocialMedia> create(@RequestBody SocialMedia socialMedia) {
        return ResponseEntity.ok(socialMediaRepository.save(socialMedia));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SocialMedia> update(@PathVariable Long id, @RequestBody SocialMedia socialMedia) {
        SocialMedia existing = socialMediaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Social media not found: " + id));
        existing.setPlatform(socialMedia.getPlatform());
        existing.setUrl(socialMedia.getUrl());
        existing.setActive(socialMedia.getActive());
        return ResponseEntity.ok(socialMediaRepository.save(existing));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        socialMediaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
