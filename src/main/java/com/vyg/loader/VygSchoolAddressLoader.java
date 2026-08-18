package com.vyg.loader;

import com.vyg.entity.Address;
import com.vyg.entity.VygSchool;
import com.vyg.enumerator.Branch;
import com.vyg.enumerator.Province;
import com.vyg.repository.AddressRepository;
import com.vyg.repository.VygSchoolRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@Order(3)
@RequiredArgsConstructor
public class VygSchoolAddressLoader implements CommandLineRunner {

    private final VygSchoolRepository vygSchoolRepository;
    private final AddressRepository addressRepository;

    @Override
    public void run(String... args) {
        Address parkStation = addressRepository
                .findByProvinceAndBranch(Province.GAUTENG, Branch.PARK_STATION)
                .orElse(null);

        if (parkStation == null) {
            log.warn("Park Station address not found. Skipping school address assignment.");
            return;
        }

        List<VygSchool> schoolsWithoutAddress = vygSchoolRepository.findByAddressIsNull();
        if (schoolsWithoutAddress.isEmpty()) {
            log.info("All schools already have an address assigned. Skipping.");
            return;
        }

        schoolsWithoutAddress.forEach(school -> school.setAddress(parkStation));
        vygSchoolRepository.saveAll(schoolsWithoutAddress);
        log.info("Assigned Park Station address to {} schools.", schoolsWithoutAddress.size());
    }
}
