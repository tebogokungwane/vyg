package com.vyg.model;

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
    private String cellNumber;
    private Long provinceId;
    private Long regionId;
    private Long branchId;  // ✅ Link Mentor to a Branch
    private Role role;
    private long mentorId;
    private Nation nation;
    private String password;
    private boolean isActive;
}
