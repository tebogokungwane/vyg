package com.vyg.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Schools {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String schoolName;
    private String schoolAddress;
    private String personToContact;
    private String mentor;
    private String createBy;
    private String contactDetails;
    private LocalDateTime dateCreated;

    @ManyToOne
    @JoinColumn(name = "address_id")  // foreign key
    private Address address;

}
