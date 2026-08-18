package com.vyg.service;

import com.vyg.dto.AdvertisementDTO;
import com.vyg.entity.Advertisement;
import com.vyg.repository.AdvertisementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdvertisementService {

    private final AdvertisementRepository advertisementRepository;

    public List<AdvertisementDTO> listAll() {
        return advertisementRepository.findAll().stream()
                .map(this::toDTO)
                .toList();
    }

    public Optional<Advertisement> getActive() {
        return advertisementRepository.findByActiveTrue();
    }

    public Optional<Advertisement> getById(Long id) {
        return advertisementRepository.findById(id);
    }

    @Transactional
    public Advertisement upload(MultipartFile file) throws IOException {
        Advertisement ad = new Advertisement();
        ad.setFilename(file.getOriginalFilename());
        ad.setContentType(file.getContentType());
        ad.setData(file.getBytes());
        ad.setActive(false);
        return advertisementRepository.save(ad);
    }

    @Transactional
    public void activate(Long id) {
        Advertisement ad = advertisementRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Advertisement not found with id: " + id));
        advertisementRepository.deactivateAll();
        ad.setActive(true);
        advertisementRepository.save(ad);
    }

    @Transactional
    public void delete(Long id) {
        Advertisement ad = advertisementRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Advertisement not found with id: " + id));
        advertisementRepository.delete(ad);
    }

    private AdvertisementDTO toDTO(Advertisement ad) {
        return AdvertisementDTO.builder()
                .id(ad.getId())
                .active(ad.getActive())
                .filename(ad.getFilename())
                .createdAt(ad.getCreatedAt())
                .build();
    }
}
