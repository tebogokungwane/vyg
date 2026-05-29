package com.vyg.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "nations")
@Setter
@Getter
public class Nations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nation;

    @Column(name = "image_data")
    private byte[] imageData;



    @Column(name = "image_type")
    private String imageType;

    @Column(name = "image_name")
    private String imageName;

    //private boolean deleted = false;

    @Column(name = "total_points")
    private int totalPoints = 0; // ✅ ADD THIS

//    // REQUIRED by JPA
    public Nations() {
    }

    // ✅ ADD THIS CONSTRUCTOR
    public Nations(String nation) {
        this.nation = nation;
    }
}
