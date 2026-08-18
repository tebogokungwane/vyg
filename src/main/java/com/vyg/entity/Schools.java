package com.vyg.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Schools {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String schoolCode;

    private String schoolName;
    private String schoolAddress;
    private String personToContact;
    private String mentor;
    private String createBy;
    private String contactDetails;
    private String status;
    private LocalDateTime dateCreated;

    private String contactTeacherName;
    private String contactTeacherPhone;
    private String contactTeacherEmail;
    private String principalName;
    private String principalPhone;
    private String principalEmail;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToOne(optional = true)
    @JoinColumn(name = "school_institution_id", nullable = true)
    private SchoolInstitution schoolInstitution;

    @PrePersist
    public void prePersist() {
        if (this.schoolCode == null || this.schoolCode.isBlank()) {
            this.schoolCode = UUID.randomUUID().toString().substring(0, 8);
        }
        if (this.status == null || this.status.isBlank()) {
            this.status = "A-ACT";
        }
        if (this.dateCreated == null) {
            this.dateCreated = LocalDateTime.now();
        }
    }
}
