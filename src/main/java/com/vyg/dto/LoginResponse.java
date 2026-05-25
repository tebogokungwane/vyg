package com.vyg.dto;

import com.vyg.entity.Members;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private String token;
    private Members member;

}
