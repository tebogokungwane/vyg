package com.vyg.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String churchName;
    private String address;
    private double latitude;
    private double longitude;

    @ManyToOne
    @JoinColumn(name = "region_id", nullable = false)
    private Region region; // ✅ Each Branch belongs to one Region
}
