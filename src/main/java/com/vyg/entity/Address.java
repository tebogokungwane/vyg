package com.vyg.entity;

import com.vyg.enumerator.Branch;
import com.vyg.enumerator.Province;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Province province;

    @Enumerated(EnumType.STRING)
    private Branch branch;

    private String fullAddress;


    public Address(Province province, Branch branch, String fullAddress) {
        this.province = province;
        this.branch = branch;
        this.fullAddress = fullAddress;
    }

}
