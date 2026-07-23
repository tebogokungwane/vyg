package com.vyg.dto;

import com.vyg.entity.Members;
import com.vyg.enumerator.Gender;
import com.vyg.enumerator.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {

    private String token;
    private MemberSummary member;

    /**
     * Lightweight member summary for login response.
     * Avoids serializing full entity graph (address, nation, schoolInstitution).
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class MemberSummary {
        private Long id;
        private String name;
        private String surname;
        private String email;
        private Gender gender;
        private String cellNumber;
        private Role role;
        private boolean active;
        private Long addressId;
        private String addressBranch;
        private String addressProvince;
        private Long nationId;
        private String nationName;
    }

    /**
     * Factory method to build LoginResponse from entity + token.
     */
    public static LoginResponse from(String token, Members member) {
        MemberSummary summary = MemberSummary.builder()
                .id(member.getId())
                .name(member.getName())
                .surname(member.getSurname())
                .email(member.getEmail())
                .gender(member.getGender())
                .cellNumber(member.getCellNumber())
                .role(member.getRole())
                .active(member.isActive())
                .addressId(member.getAddress() != null ? member.getAddress().getId() : null)
                .addressBranch(member.getAddress() != null ? String.valueOf(member.getAddress().getBranch()) : null)
                .addressProvince(member.getAddress() != null ? String.valueOf(member.getAddress().getProvince()) : null)
                .nationId(member.getNation() != null ? member.getNation().getId() : null)
                .nationName(member.getNation() != null ? member.getNation().getNation() : null)
                .build();

        return LoginResponse.builder()
                .token(token)
                .member(summary)
                .build();
    }
}
