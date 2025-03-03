package com.vyg.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "events")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "base_event_id", nullable = false)
    private BaseEvent baseEvent; // ✅ Reference to BaseEvent (Event type)

    private int participants; // ✅ Number of people who participated
    private int totalPoints; // ✅ Total points earned for the event
    private boolean deleted = false; // ✅ Soft delete indicator

    @ManyToOne
    @JoinColumn(name = "nation_points_id")
    private NationPoints nationPoints; // ✅ Link each event's points to a Nation

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Members member; // ✅ Link the event to a participating member

    @PrePersist
    @PreUpdate
    public void calculateTotalPoints() {
        this.totalPoints = this.baseEvent.getDefaultPoints() * this.participants;
    }
}
