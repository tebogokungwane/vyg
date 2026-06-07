package com.vyg.dto;

import lombok.Data;

@Data
public class ProjectDTO {
    private Long id;
    private String name;
    private String imageName;
    private int memberCount;
    // getters and setters
}