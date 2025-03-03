package com.vyg.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // e.g., "Pretoria", "Durban", "Cape Town"

    @ManyToOne
    @JoinColumn(name = "province_id", nullable = false)
    private Province province; // ✅ Each Region belongs to a Province

    @JsonIgnore // ✅ Prevents infinite recursion
    @OneToMany(mappedBy = "region", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Branch> branches; // ✅ One Region has many Branches
}
