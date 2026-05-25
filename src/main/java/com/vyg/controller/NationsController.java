//package com.vyg.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.vyg.entity.Nations;
//import com.vyg.mapper.NationMapper;
//import com.vyg.dto.NationDTO;
//import com.vyg.service.NationsService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.server.ResponseStatusException;
//import java.util.List;
//
//@Slf4j
//@RestController
//@RequestMapping("/api/nations")
//@RequiredArgsConstructor
//public class NationsController {
//
//    private final NationsService nationsService;
//
//    @GetMapping("/raw")
//    public List<Nations> getAllAddresses() {
//
//        return nationsService.getAllAddresses();
//    }
//
//    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<?> createNation(
//            @RequestParam("nation") String nationName,
//            @RequestParam("imageFile") MultipartFile imageFile) {
//
//        // ADD LOGGERS HERE
//        log.info("LOG: Received request to create nation");
//        log.info("LOG: Nation Name: {}", nationName);
//        log.info("LOG: File Name: {}", imageFile != null ? imageFile.getOriginalFilename() : "NULL");
//        log.info("LOG: File Content-Type: {}", imageFile != null ? imageFile.getContentType() : "N/A");
//
//        try {
//            Nations nation = new Nations();
//            nation.setNation(nationName);
//            Nations saved = nationsService.createNation(nation, imageFile);
//            return new ResponseEntity<>(saved, HttpStatus.CREATED);
//        } catch (Exception e) {
//            log.error("LOG ERROR: Failed to create nation", e); // Log the full stack trace
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//
//
//
//
//    @GetMapping("/{id}/image")
//    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
//
//        log.info("📥 Request received for image of nation id={}", id);
//
//        Nations nation = nationsService.getNationById(id);
//
//        if (nation == null) {
//            log.warn("❌ Nation not found for id={}", id);
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nation not found");
//        }
//
//        byte[] imageData = nation.getImageData();
//        String imageType = nation.getImageType();
//
//        log.info("🗄 Retrieved nation: id={}, name={}", nation.getId(), nation.getNation());
//
//        if (imageData == null) {
//            log.warn("❌ Image data is NULL for nation id={}", id);
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No image found");
//        }
//
//        log.info("🖼 Image found → size={} bytes, type={}",
//                imageData.length,
//                imageType
//        );
//
//        return ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType(imageType))
//                .body(imageData);
//    }
//
//
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Nations> getNationById(@PathVariable Long id) {
//        try {
//            Nations nation = nationsService.getNationById(id);
//            return ResponseEntity.ok(nation);
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
//    }
//
//    @GetMapping
//    public List<NationDTO> getAllNations(){
//        return nationsService.getAllAddresses()
//                .stream()
//                .map(NationMapper::mapToDto)
//                .toList();
//    }
//
//
//    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<Nations> updateNation(
//            @PathVariable Long id,
//            @RequestPart("nation") String nationJson,
//            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) {
//
//        try {
//            log.info("---Inside image update");
//            ObjectMapper mapper = new ObjectMapper();
//            Nations updatedData = mapper.readValue(nationJson, Nations.class);
//
//            Nations updatedNation = nationsService.updateNation(id, updatedData, imageFile);
//            return ResponseEntity.ok(updatedNation);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteNation(@PathVariable Long id){
//        try{
//            nationsService.deleteNation(id);
//            System.out.println("about to delete nation");
//            return ResponseEntity.ok("nation deleted successfully.");
//
//        }catch (Exception e){
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Failed to delete nation: "+ e.getMessage());
//        }
//    }
//
//    @PutMapping("/{id}/name")
//    public ResponseEntity<?> updateNationName(@PathVariable Long id, @RequestBody NationDTO nation) {
//        try {
//            Nations updated = nationsService.updateNationName(id, nation.getNation());
//            return ResponseEntity.ok(updated);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Failed to update nation name: " + e.getMessage());
//        }
//    }
//
//
//
//
//}
