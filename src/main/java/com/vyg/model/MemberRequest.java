package com.vyg.model;

import com.vyg.entity.Nations;
import com.vyg.enumerator.Gender;
import com.vyg.enumerator.Role;
import com.vyg.enumerator.Nation;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberRequest {
    private String name;
    private String surname;
    private Gender gender;
    private String email;
    private String cellNumber;
    private Long addressId;
    private Role role;
    private long mentorId;
    private Nations nation;
    private String password;
    private boolean isActive;
    private String residentialAddress;
    private String createBy;

}
