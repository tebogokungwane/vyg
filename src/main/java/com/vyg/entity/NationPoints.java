package com.vyg.entity;

import com.vyg.enumerator.Nation;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "nation_points")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NationPoints {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String province;
    private String region;

    @Enumerated(EnumType.STRING)
    private Nation nation;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private BaseEvent event;

    private int weekNumber; // ✅ Tracks which week the points were earned
    private int pointsEarned; // ✅ Points earned by the nation for this event
}
