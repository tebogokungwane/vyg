package com.vyg.service;

import com.vyg.entity.Branding;
import com.vyg.repository.BrandingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class BrandingService {

    private final BrandingRepository brandingRepository;

    @Transactional
    public Branding upload(String type, MultipartFile file) {
        if (!type.equals("favicon") && !type.equals("logo") && !type.equals("background")) {
            throw new IllegalArgumentException("Type must be 'favicon', 'logo', or 'background'");
        }

        Branding branding = brandingRepository.findByType(type).orElse(new Branding());
        branding.setType(type);
        branding.setFileName(file.getOriginalFilename());
        branding.setImageType(file.getContentType());

        try {
            branding.setImageData(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file", e);
        }

        return brandingRepository.save(branding);
    }

    @Transactional(readOnly = true)
    public Branding getByType(String type) {
        return brandingRepository.findByType(type)
                .orElseThrow(() -> new IllegalArgumentException("No " + type + " uploaded"));
    }
}
