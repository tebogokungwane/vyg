package com.vyg.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "branding")
@Getter
@Setter
@NoArgsConstructor
public class Branding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String type; // "favicon" or "logo"

    @Column(name = "image_data", nullable = false, columnDefinition = "bytea")
    private byte[] imageData;

    @Column(name = "image_type", nullable = false)
    private String imageType;

    @Column(name = "file_name")
    private String fileName;
}
