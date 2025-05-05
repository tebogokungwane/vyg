package com.vyg.service;

import com.vyg.entity.Nations;
import com.vyg.repository.NationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;


@Service
public class NationsService {

    @Value("#${upload.dir}")
    private String uploadDir;

    @Autowired
    private NationsRepository nationsRepository ;

    public List<Nations> getAllAddresses() {
        return nationsRepository.findAll();
    }

    public Nations createNation(Nations nation, MultipartFile imageFile) throws IOException {

        System.out.println("➡️ Image file name: " + imageFile.getOriginalFilename());
        System.out.println("➡️ Image type: " + imageFile.getContentType());
        System.out.println("➡️ Image size: " + imageFile.getSize());


       nation.setImageName(imageFile.getOriginalFilename());
       nation.setImageType(imageFile.getContentType());
       nation.setImageData(imageFile.getBytes());

        return nationsRepository.save(nation);
    }

    public Nations getNationById(Long id) {
        Nations nation = nationsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nation not found"));

        return nation;
    }

    public Nations updateNation(Long id, Nations updatedData, MultipartFile imageFile) throws IOException {
        Nations existing = nationsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nation not found"));

        // Update nation name
        existing.setNation(updatedData.getNation());

        // Update image if provided
        if (imageFile != null && !imageFile.isEmpty()) {
            existing.setImageName(imageFile.getOriginalFilename());
            existing.setImageType(imageFile.getContentType());
            existing.setImageData(imageFile.getBytes());
        }

        return nationsRepository.save(existing);
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
