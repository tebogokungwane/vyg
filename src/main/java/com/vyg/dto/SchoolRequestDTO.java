package com.vyg.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchoolRequestDTO {

    private String schoolName;
    private String schoolAddress;
    private String personToContact;
    private String mentor;
    private String contactDetails;
    private Long addressId;
    private String createBy;
}
