package com.vyg.entity;

import com.vyg.enumerator.Branch;
import com.vyg.enumerator.Province;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Church {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Province province;

    private String region; // Optional: make it a string or enum

    @Enumerated(EnumType.STRING)
    private Branch branch;

    private String fullAddress;
}
