package com.vyg.service;

import com.vyg.entity.Nations;
import com.vyg.repository.NationsRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class NationsService {

    @Value("#${upload.dir}")
    private String uploadDir;

    @Autowired
    private NationsRepository nationsRepository ;

    public List<Nations> getAllAddresses() {
        return nationsRepository.findAll();
    }

    @Transactional
    public Nations createNation(Nations nation, MultipartFile imageFile) throws IOException {
        if (imageFile != null && !imageFile.isEmpty()) {
            log.info("LOG: Processing image bytes for nation: {}", nation.getNation());
            nation.setImageData(imageFile.getBytes());
            nation.setImageType(imageFile.getContentType());
            nation.setImageName(imageFile.getOriginalFilename());
        } else {
            log.warn("LOG: No image file provided for nation: {}", nation.getNation());
        }
        return nationsRepository.saveAndFlush(nation);
    }



    public Nations getNationById(Long id) {
        Nations nation = nationsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nation not found"));

        return nation;
    }

    @Transactional
    public Nations updateNation(Long id, Nations updated, MultipartFile imageFile) throws IOException {

        Nations nation = nationsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nation not found"));

        nation.setNation(updated.getNation());

        if (imageFile != null && !imageFile.isEmpty()) {
            nation.setImageData(imageFile.getBytes()); // ✅
            
            nation.setImageType(imageFile.getContentType());
            nation.setImageName(imageFile.getOriginalFilename());
        }

        return nationsRepository.save(nation);
    }


    public Nations updateNationName(Long id, String newName){
        Nations nations = nationsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nation not found"));
        nations.setNation(newName);

        return nationsRepository.save(nations);

    }


    public void deleteNation(Long id){
        Nations nation = nationsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nation not found"));

        nationsRepository.delete(nation);
    }


}
