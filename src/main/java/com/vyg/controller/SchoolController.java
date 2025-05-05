package com.vyg.controller;

import com.vyg.entity.Schools;
import com.vyg.dto.SchoolRequestDTO;
import com.vyg.service.SchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schools")
@RequiredArgsConstructor
public class SchoolController {

    private final SchoolService schoolService;

   @PostMapping("/register")
    public ResponseEntity<Schools> saveSchools(@Validated @RequestBody SchoolRequestDTO schoolRequestDTO){
       Schools school = schoolService.saveSchool(schoolRequestDTO);
       return ResponseEntity.status(HttpStatus.CREATED).body(school);
   }

   @GetMapping("/{addressId}")
    public List<Schools> findSchoolsByAddress(@PathVariable Long addressId){
       return ResponseEntity.ok(schoolService.listOfSchools(addressId)).getBody();
   }

    @PutMapping("/{id}")
    public ResponseEntity<Schools> updateSchool(@PathVariable Long id, @RequestBody SchoolRequestDTO schoolRequestDTO){
       return ResponseEntity.ok(schoolService.updateSchool(id,schoolRequestDTO));
    }

}
