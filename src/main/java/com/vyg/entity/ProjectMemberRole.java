package com.vyg.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "project_member_roles")
public class ProjectMemberRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Members member;

    @Column(nullable = false)
    private String role; // LEADER, COORDINATOR, MEMBER

    @Column(nullable = false)
    private String level; // BRANCH, REGION, PROVINCE, COUNTRY

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;
}
