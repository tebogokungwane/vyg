package com.vyg.controller;

import com.vyg.entity.Address;
import com.vyg.dto.AddressDTO;
import com.vyg.repository.AddressRepository;
import com.vyg.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private AddressRepository addressRepository;

    @GetMapping
    public List<Address> getAllAddresses() {
        return addressService.getAllAddresses();
    }


    @PostMapping("/create")
    public ResponseEntity<Address> createAddress(@RequestBody AddressDTO addressDTO){
        return ResponseEntity.ok(addressService.saveAddress(addressDTO));
    }

    @PutMapping("/{id}/coordinates")
    public ResponseEntity<?> updateCoordinates(@PathVariable Long id, @RequestBody Map<String, Double> coords) {
        Address address = addressRepository.findById(id).orElseThrow();
        address.setLatitude(coords.get("latitude"));
        address.setLongitude(coords.get("longitude"));
        addressRepository.save(address);
        return ResponseEntity.ok(address);
    }
}
