package com.vyg.controller;

import com.vyg.entity.Address;
import com.vyg.entity.Nations;
import com.vyg.service.AddressService;
import com.vyg.service.NationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/nations")
public class NationsController {

    @Autowired
    private NationsService nationsService;

    @GetMapping
    public List<Nations> getAllAddresses() {

        return nationsService.getAllAddresses();
    }
}
