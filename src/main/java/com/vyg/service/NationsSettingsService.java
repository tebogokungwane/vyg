package com.vyg.service;

import com.vyg.dto.NationRequest;
import com.vyg.entity.Nations;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface NationsSettingsService {


    Nations create(String request, MultipartFile image);

    Nations updateName(Long id, String name);

    Nations updateImage(Long id, MultipartFile image);

    Nations getById(Long id);

    List<Nations> getAll();

    void delete(Long id);
}
