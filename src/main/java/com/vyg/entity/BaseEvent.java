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
//        private boolean isVisible;

        public BaseEvent(String name, int defaultPoints) {
            this.name = name;
            this.defaultPoints = defaultPoints;
//            this.isVisible = isVisible;
        }
    }

