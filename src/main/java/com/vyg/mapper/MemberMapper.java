package com.vyg.mapper;

import com.vyg.entity.Address;
import com.vyg.entity.Members;
import com.vyg.dto.MemberRequest;
import com.vyg.entity.Nations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

public class MemberMapper {

    public static Members toEntity(
            MemberRequest memberRequest,
            Address address,
            Members mentor,
            Nations nation,
            PasswordEncoder passwordEncoder
    ) {

        Members members = new Members();

        members.setName(capitalizeFirstLetter(memberRequest.getName()));
        members.setSurname(capitalizeFirstLetter(memberRequest.getSurname()));
        members.setGender(memberRequest.getGender());
        members.setEmail(memberRequest.getEmail());
        members.setCellNumber(memberRequest.getCellNumber());
        members.setAddress(address);
        members.setRole(memberRequest.getRole());
        members.setMentor(mentor);
        members.setCapturedBy(memberRequest.getCreatedBy());
        members.setNation(nation);

        String rawPassword = memberRequest.getPassword() != null
                ? memberRequest.getPassword()
                : "VYG@123";
        members.setPassword(passwordEncoder.encode(rawPassword));

        members.setActive(true);
        members.setResidentialAddress(memberRequest.getResidentialAddress());
        members.setDateCreated(LocalDateTime.now());

        return members;
    }

    private static String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase()
                + input.substring(1).toLowerCase();
    }

}
