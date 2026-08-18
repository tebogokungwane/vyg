package com.vyg.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "school_institution")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchoolInstitution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nat_emis", unique = true)
    private Long natEmis;

    @Column(name = "data_year")
    private Integer dataYear;

    private String province;

    @Column(name = "official_institution_name", length = 500)
    private String officialInstitutionName;

    private String status;

    private String sector;

    @Column(name = "type_doe", length = 100)
    private String typeDoe;

    @Column(name = "phase_ped", length = 100)
    private String phasePed;

    private String specialisation;

    @Column(name = "ei_district", length = 100)
    private String eiDistrict;

    @Column(name = "ei_circuit", length = 100)
    private String eiCircuit;

    @Column(name = "owner_land", length = 100)
    private String ownerLand;

    @Column(name = "owner_build", length = 100)
    private String ownerBuild;

    @Column(name = "ex_dept", length = 200)
    private String exDept;

    @Column(name = "persal_paypoint_no", length = 50)
    private String persalPaypointNo;

    @Column(name = "persal_component_no", length = 50)
    private String persalComponentNo;

    @Column(name = "exam_no", length = 50)
    private String examNo;

    @Column(name = "exam_centre", length = 200)
    private String examCentre;

    @Column(name = "gis_longitude")
    private Double gisLongitude;

    @Column(name = "gis_latitude")
    private Double gisLatitude;

    @Column(name = "district_municipality_name", length = 500)
    private String districtMunicipalityName;

    @Column(name = "local_municipality_name", length = 500)
    private String localMunicipalityName;

    @Column(name = "ward_id", length = 50)
    private String wardId;

    @Column(name = "sp_code", length = 50)
    private String spCode;

    @Column(name = "sp_name", length = 200)
    private String spName;

    @Column(length = 500)
    private String addressee;

    @Column(name = "township_village", length = 200)
    private String townshipVillage;

    @Column(length = 200)
    private String suburb;

    @Column(name = "town_city", length = 200)
    private String townCity;

    @Column(name = "street_address", length = 1000)
    private String streetAddress;

    @Column(name = "postal_address", length = 500)
    private String postalAddress;

    private String email;

    @Column(length = 100)
    private String telephone;

    @Column(length = 10)
    private String section21;

    @Column(name = "section21_function", length = 50)
    private String section21Function;

    @Column(length = 20)
    private String quintile;

    @Column(length = 100)
    private String nas;

    @Column(name = "nodal_area", length = 100)
    private String nodalArea;

    @Column(name = "registration_date", length = 100)
    private String registrationDate;

    @Column(name = "no_fee_school", length = 10)
    private String noFeeSchool;

    @Column(name = "urban_rural", length = 20)
    private String urbanRural;

    @Column(length = 50)
    private String allocation;

    @Column(name = "demarcation_from", length = 50)
    private String demarcationFrom;

    @Column(name = "demarcation_to", length = 50)
    private String demarcationTo;

    @Column(name = "old_nat_emis", length = 50)
    private String oldNatEmis;

    @Column(name = "new_nat_emis", length = 50)
    private String newNatEmis;

    private Integer learners2025;

    private Integer educators2025;
}
