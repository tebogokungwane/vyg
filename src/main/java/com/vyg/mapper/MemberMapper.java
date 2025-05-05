package com.vyg.mapper;

import com.vyg.entity.Address;
import com.vyg.entity.Members;
import com.vyg.dto.MemberRequest;

import java.time.LocalDateTime;

public class MemberMapper {




    public static Members toEntity(MemberRequest memberRequest, Address address, Members mentor){

        Members members = new Members();
        LocalDateTime localDateTime = LocalDateTime.now();

        members.setName(capitalizeFirstLetter(memberRequest.getName()));
        members.setSurname(capitalizeFirstLetter(memberRequest.getSurname()));
        members.setGender(memberRequest.getGender());
        members.setEmail(memberRequest.getEmail());
        members.setCellNumber(memberRequest.getCellNumber());
        members.setAddress(address);
        members.setRole(memberRequest.getRole());
        members.setMentor(mentor);
        members.setNation(memberRequest.getNation());
        members.setPassword("VYG@123");
        members.setActive(true);
        members.setResidentialAddress(memberRequest.getResidentialAddress());
        members.setCapturedBy(memberRequest.getCreateBy());
        members.setDateCreated(localDateTime);

        return members;

    }

    private static String capitalizeFirstLetter(String input){
        if(input == null || input.isEmpty()){
            return input;
        }
        return input.substring(0,1).toUpperCase() + input.substring(1).toLowerCase();
    }

}
