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

        private String name;
        private int defaultPoints;

//        @ManyToOne
//        @JoinColumn(name = "event_id", nullable = true)
//        private BaseEvent parentEvent;


        public BaseEvent(String name, int defaultPoints) {
            this.name = name;
            this.defaultPoints = defaultPoints;
        }
    }

