package com.vyg.entity;


    import jakarta.persistence.*;
import lombok.*;

    @Getter
    @Setter
    @Entity
    @Table(name = "base_events")
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public class BaseEvent {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String name; // ✅ Event name (e.g., "Sunday Service")
        private int defaultPoints; // ✅ Default point value (Explosion points)
        private boolean active = true; // ✅ Allows disabling an event instead of deleting it
    }

