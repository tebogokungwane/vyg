package com.vyg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vyg.entity.Nations;
import com.vyg.mapper.NationMapper;
import com.vyg.dto.NationDTO;
import com.vyg.service.NationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/api/nations")
public class NationsController {

    @Autowired
    private NationsService nationsService;

    @GetMapping("/raw")
    public List<Nations> getAllAddresses() {

        return nationsService.getAllAddresses();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createNation(
            @RequestPart("nation") String nationJson,
            @RequestPart("imageFile") MultipartFile imageFile) {

        try {
            // Convert the JSON string to Nations object
            ObjectMapper mapper = new ObjectMapper();
            Nations nation = mapper.readValue(nationJson, Nations.class);

            Nations saved = nationsService.createNation(nation, imageFile);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        Nations nation = nationsService.getNationById(id);

        if (nation.getImageData() == null || nation.getImageType() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No image found");
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(nation.getImageType()))
                .body(nation.getImageData());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Nations> getNationById(@PathVariable Long id) {
        try {
            Nations nation = nationsService.getNationById(id);
            return ResponseEntity.ok(nation);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping
    public List<NationDTO> getAllNations(){
        return nationsService.getAllAddresses()
                .stream()
                .map(NationMapper::mapToDto)
                .toList();
    }


    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Nations> updateNation(
            @PathVariable Long id,
            @RequestPart("nation") String nationJson,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            Nations updatedData = mapper.readValue(nationJson, Nations.class);

            Nations updatedNation = nationsService.updateNation(id, updatedData, imageFile);
            return ResponseEntity.ok(updatedNation);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNation(@PathVariable Long id){
        try{
            nationsService.deleteNation(id);
            System.out.println("about to delete nation");
            return ResponseEntity.ok("nation deleted successfully.");

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete nation: "+ e.getMessage());
        }
    }

    @PutMapping("/{id}/name")
    public ResponseEntity<?> updateNationName(@PathVariable Long id, @RequestBody NationDTO nation) {
        try {
            Nations updated = nationsService.updateNationName(id, nation.getNation());
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update nation name: " + e.getMessage());
        }
    }

}
