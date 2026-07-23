package com.vyg.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Schools {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String schoolName;
    private String schoolAddress;
    private String personToContact;
    private String mentor;
    private String createBy;
    private String contactDetails;
    private LocalDateTime dateCreated;

    // New fields - contact teacher info
    private String contactTeacherName;
    private String contactTeacherPhone;
    private String contactTeacherEmail;
    private String principalName;
    private String principalPhone;
    private String principalEmail;

    // Fields from school data import
    private String area;
    private String province;
    private String gradesInvolved;
    private String activeProgrammes;
    private String status;
    private String schoolImage;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToOne(optional = true)
    @JoinColumn(name = "school_institution_id", nullable = true)
    private SchoolInstitution schoolInstitution;
}
