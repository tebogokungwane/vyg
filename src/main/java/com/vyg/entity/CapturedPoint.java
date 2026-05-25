package com.vyg.entity;

import com.vyg.enumerator.ApprovalStatus;
import com.vyg.enumerator.CaptureType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
        uniqueConstraints = @UniqueConstraint(
                columnNames = {
                        "nation_id",
                        "base_event_id",
                        "weekNumber",
                        "year",
                        "approvalStatus"
                }
        )
)
public class CapturedPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Link to the Nation
    @ManyToOne
    @JoinColumn(name = "nation_id")
    private Nations nation;

    // Link to the Event
    @ManyToOne
    @JoinColumn(name = "base_event_id")
    private BaseEvent baseEvent;


    @Column(nullable = false)
    private int points;


    private int numberOfPeople;

    private int totalPointsEarnedPerWeek;

    private LocalDate dateCaptured;

    private String fullDate; // e.g., "23 January 2025"

    private int weekNumber;

    private int month;

    private int year;

    private String capturedBy; // Optional: person who submitted the data

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    // ✅ THIS IS WHAT "PENDING" MEANS
    @Enumerated(EnumType.STRING)
    private ApprovalStatus approvalStatus = ApprovalStatus.PENDING;
    private String approvedBy;
    private LocalDate approvedDate;
    @Enumerated(EnumType.STRING)
    private CaptureType captureType;



}
