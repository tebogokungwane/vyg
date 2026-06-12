package com.vyg.dto;

import lombok.Data;

@Data
public class AssignRoleRequest {
    private Long memberId;
    private String role;
    private String level;
    private Long addressId;
}
