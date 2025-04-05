package com.vyg.service;

import com.vyg.entity.Nations;
import com.vyg.repository.NationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NationsService {

    @Autowired
    private NationsRepository nationsRepository ;

    public List<Nations> getAllAddresses() {
        return nationsRepository.findAll();
    }
}
