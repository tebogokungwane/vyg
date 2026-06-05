package com.vyg.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoveMemberRequest {
    private Long memberId;
    private Long fromProjectId;
    private Long toProjectId;
}
