package com.vyg.loader;

import com.vyg.entity.Address;
import com.vyg.entity.Members;
import com.vyg.enumerator.Gender;
import com.vyg.enumerator.Role;
import com.vyg.enumerator.Branch;
import com.vyg.enumerator.Province;
import com.vyg.repository.AddressRepository;
import com.vyg.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Component
@Order(3)
@RequiredArgsConstructor
public class UserDataInitializer implements CommandLineRunner {

    private final MemberRepository memberRepository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) {
        if (memberRepository.count() > 0) {
            log.info("Default user already exists, skipping initialization.");
            return;
        }

        log.info("Initializing default system user...");

        Address savedAddress = addressRepository
                .findByProvinceAndBranch(Province.GAUTENG, Branch.SOWETO)
                .orElseThrow(() -> new RuntimeException(
                        "SOWETO address not found. Ensure AddressLoader runs first."
                ));

        Members defaultUser = Members.builder()
                .name("Tebogo")
                .surname("Kungwane")
                .cellNumber("0711382940")
                .email("kungwane@gmail.com")
                .gender(Gender.MALE)
                .isActive(true)
                .password(passwordEncoder.encode("vyg@123"))
                .role(Role.SENIOR)
                .capturedBy("system-admin")
                .residentialAddress("2669 nicol street")
                .dateCreated(LocalDateTime.now())
                .address(savedAddress)
                .build();

        memberRepository.save(defaultUser);
        log.info("Default user 'Tebogo Kungwane' seeded successfully.");
    }
}
