package com.vyg.mapper;

import com.vyg.dto.NationSettingsDTO;
import com.vyg.entity.Nations;

public class NationSettingsMapper {

    public static NationSettingsDTO toDto(Nations nation) {
        return new NationSettingsDTO(
                nation.getId(),
                nation.getNation()
        );
    }
}
