package com.vyg.entity;

import com.vyg.enumerator.AdjustmentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PointAdjustment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int points;

    @Enumerated(EnumType.STRING)
    private AdjustmentType adjustmentType; // ADD or SUBTRACT

    private String reason;
    private String capturedBy;
    private LocalDate dateCaptured;

    @ManyToOne
    private Nations nation;

    @ManyToOne
    private Address address;
}
