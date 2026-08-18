package com.vyg.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "vyg_school")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VygSchool {

    @Id
    @Column(name = "school_id")
    private String schoolId;

    @Column(name = "school_image", length = 500)
    private String schoolImage;

    @Column(name = "school_name")
    private String schoolName;

    private String area;

    @Column(length = 10)
    private String province;

    @Column(name = "contact_teacher_name", length = 100)
    private String contactTeacherName;

    @Column(name = "contact_teacher_phone", length = 20)
    private String contactTeacherPhone;

    @Column(name = "contact_teacher_email")
    private String contactTeacherEmail;

    @Column(name = "grades_involved", length = 500)
    private String gradesInvolved;

    @Column(name = "active_programmes", length = 500)
    private String activeProgrammes;

    @Column(length = 20)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "school", fetch = FetchType.LAZY)
    private List<VygLearner> learners;
}
