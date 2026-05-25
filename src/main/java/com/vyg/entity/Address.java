package com.vyg.entity;

import com.vyg.enumerator.Branch;
import com.vyg.enumerator.Province;
import jakarta.persistence.*;
import lombok.*;

@ToString(exclude = {"address"}) // OPTION 1: Exclude here
@EqualsAndHashCode(exclude = {"address"}) // Also recommended for safety



@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // Recommended for seeding
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address; // Parent address, optional

    @Enumerated(EnumType.STRING)
    private Province province;

    @Enumerated(EnumType.STRING)
    private Branch branch;

    private String fullAddress;

    // Manual constructor for standard fields (avoiding 'address')
    public Address(Province province, Branch branch, String fullAddress) {
        this.province = province;
        this.branch = branch;
        this.fullAddress = fullAddress;
    }
}
