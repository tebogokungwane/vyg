package com.vyg.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchoolInstitutionRequestDTO {
    private Long natEmis;
    private Integer dataYear;
    private String province;
    private String officialInstitutionName;
    private String status;
    private String sector;
    private String typeDoe;
    private String phasePed;
    private String specialisation;
    private String eiDistrict;
    private String eiCircuit;
    private String ownerLand;
    private String ownerBuild;
    private String exDept;
    private String persalPaypointNo;
    private String persalComponentNo;
    private String examNo;
    private String examCentre;
    private Double gisLongitude;
    private Double gisLatitude;
    private String districtMunicipalityName;
    private String localMunicipalityName;
    private String wardId;
    private String spCode;
    private String spName;
    private String addressee;
    private String townshipVillage;
    private String suburb;
    private String townCity;
    private String streetAddress;
    private String postalAddress;
    private String email;
    private String telephone;
    private String section21;
    private String section21Function;
    private String quintile;
    private String nas;
    private String nodalArea;
    private String registrationDate;
    private String noFeeSchool;
    private String urbanRural;
    private String allocation;
    private String demarcationFrom;
    private String demarcationTo;
    private String oldNatEmis;
    private String newNatEmis;
    private Integer learners2025;
    private Integer educators2025;
}
