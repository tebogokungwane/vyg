package com.vyg.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vyg_learner")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VygLearner {

    @Id
    @Column(name = "learner_id")
    private String learnerId;

    @Column(name = "full_name")
    private String fullName;

    @Column(length = 10)
    private String grade;

    @Column(length = 10)
    private String gender;

    @Column(name = "programme_interests", length = 50)
    private String programmeInterests;

    @Column(name = "needs_mentor")
    private Boolean needsMentor;

    @Column(length = 20)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "school_id", nullable = false)
    private VygSchool school;
}
