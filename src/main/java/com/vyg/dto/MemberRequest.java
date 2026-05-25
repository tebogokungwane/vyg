package com.vyg.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vyg.entity.Nations;
import com.vyg.enumerator.Gender;
import com.vyg.enumerator.Role;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MemberRequest {

    private String name;
    private String surname;
    private Gender gender;
    private String email;
    private String cellNumber;

    private Long addressId;

    private Role role;

    private Long mentorId;

    //private Nations nation;
    private Long nationId;   // ✅ NOT Nation

    private String password;
    private String residentialAddress;
    private String createdBy;
    private boolean isActive;
}
