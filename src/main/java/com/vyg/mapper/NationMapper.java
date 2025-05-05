package com.vyg.mapper;

import com.vyg.entity.Nations;
import com.vyg.dto.NationDTO;

public class NationMapper {

    public static NationDTO mapToDto(Nations nations){
        NationDTO nationDTO = new NationDTO();
        nationDTO.setId(nations.getId());
        nationDTO.setNation(nations.getNation());
        nationDTO.setImageName(nations.getImageName());
        nationDTO.setImageType(nations.getImageType());
        nationDTO.setTotalPoints(nations.getTotalPoints());

        if(nations.getImageData() != null){
            nationDTO.setImageUrl("http://localhost:2025/api/nations/" + nations.getId() + "/image");
        }

        return nationDTO;

    }



}
