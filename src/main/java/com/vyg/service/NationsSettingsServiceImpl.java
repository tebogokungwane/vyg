package com.vyg.service;

import com.vyg.entity.Nations;
import com.vyg.repository.NationsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Service // CRITICAL: Add this annotation

@RequiredArgsConstructor
public class NationsSettingsServiceImpl implements NationsSettingsService {

    private final NationsRepository nationRepository;

    @Override
    public Nations create(String request, MultipartFile image) {
        Nations nation = new Nations();
        nation.setNation(request);

        if (image != null) {
            setImage(nation, image);
        }

        return nationRepository.save(nation);
    }

    @Override
    public Nations updateName(Long id, String name) {
        Nations nation = getById(id);
        nation.setNation(name);
        return nationRepository.save(nation);
    }

    @Override
    public Nations updateImage(Long id, MultipartFile image) {
        Nations nation = getById(id);
        setImage(nation, image);
        return nationRepository.save(nation);
    }

    @Override
    public Nations getById(Long id) {
        return nationRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Nation not found"));
    }

    @Override
    public List<Nations> getAll() {
        return nationRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        nationRepository.deleteById(id);
    }

    private void setImage(Nations nation, MultipartFile image) {
        try {
            nation.setImageData(image.getBytes());
            nation.setImageType(image.getContentType());
        } catch (Exception e) {
            throw new RuntimeException("Failed to process image");
        }
    }
}
