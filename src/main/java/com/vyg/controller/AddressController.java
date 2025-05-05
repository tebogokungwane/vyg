package com.vyg.controller;

import com.vyg.entity.Address;
import com.vyg.dto.AddressDTO;
import com.vyg.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping
    public List<Address> getAllAddresses() {
        return addressService.getAllAddresses();
    }


    @PostMapping("/create")
    public ResponseEntity<Address> createAddress(@RequestBody AddressDTO addressDTO){
        return ResponseEntity.ok(addressService.saveAddress(addressDTO));
    }
}
