package com.vyg.entity;


import com.vyg.enumerator.ApprovalStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "manual_adjustments")
public class ManualAdjustment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Which nation this adjustment applies to
    @ManyToOne
    @JoinColumn(name = "nation_id", nullable = false)
    private Nations nation;

    // Church / address context
    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    // Points being manually adjusted (+ or -)
    @Column(nullable = false)
    private int points;

    // Explanation / reason
    @Column(nullable = false, length = 500)
    private String reason;

    // Who requested the adjustment
    @Column(nullable = false)
    private String requestedBy;

    // Date of request
    private LocalDate requestedDate;

    // Approval workflow
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApprovalStatus approvalStatus = ApprovalStatus.PENDING;

    // Admin who approved / rejected
    private String approvedBy;

    // Date of approval / rejection
    private LocalDate approvedDate;

    @ManyToOne
    @JoinColumn(name = "base_event_id")
    private BaseEvent baseEvent;

    @Column(name = "week_number")
    private Integer weekNumber;

    @Column(name = "year")
    private Integer year;


}
