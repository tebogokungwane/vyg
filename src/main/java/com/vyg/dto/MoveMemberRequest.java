package com.vyg.dto;

import lombok.Data;

@Data
public class MoveMemberRequest {
    private Long memberId;
    private Long fromProjectId;
    private Long toProjectId;
    // getters and setters
}