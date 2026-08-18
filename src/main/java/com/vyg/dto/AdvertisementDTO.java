package com.vyg.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class AdvertisementDTO {

    private Long id;
    private Boolean active;
    private String filename;
    private LocalDate createdAt;
}
