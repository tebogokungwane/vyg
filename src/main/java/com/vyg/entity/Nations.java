package com.vyg.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor


public class Nations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nation;
    private String imageName;
    private String imageType;
    @Column(name = "total_points")
    private int totalPoints;

    @Lob
    @JsonIgnore
    private byte[] imageData;


    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;


    public Nations(Object o, String name) {
    }
}
