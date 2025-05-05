package com.vyg.dto;

import com.vyg.enumerator.Branch;
import com.vyg.enumerator.Province;
import lombok.Data;

@Data
public class Address {
    private Province province;
    private Branch branch;
    private String fullAddress;

    public Address(Province province, Branch branch, String fullAddress) {
        this.province = province;
        this.branch = branch;
        this.fullAddress = fullAddress;
    }

    // Getters and Setters
}
