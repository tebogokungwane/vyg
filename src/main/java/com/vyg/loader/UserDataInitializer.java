package com.vyg.loader;

import com.vyg.entity.Address;
import com.vyg.entity.Members;
import com.vyg.enumerator.Gender;
import com.vyg.enumerator.Role;
import com.vyg.enumerator.Branch;
import com.vyg.enumerator.Province;
import com.vyg.repository.AddressRepository;
import com.vyg.repository.MemberRepository;
import com.vyg.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Component
@Order(3)
@RequiredArgsConstructor
public class UserDataInitializer implements CommandLineRunner {

    private final MemberRepository memberRepository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    private String generatePassword() {
        return UUID.randomUUID().toString().substring(0, 6);
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (memberRepository.count() > 0) {
            log.info("Default user already exists, skipping initialization.");
            seedParkStationMember();
            return;
        }

        log.info("Initializing default system user...");

        Address savedAddress = addressRepository
                .findByProvinceAndBranch(Province.GAUTENG, Branch.SOWETO)
                .orElseThrow(() -> new RuntimeException(
                        "SOWETO address not found. Ensure AddressLoader runs first."
                ));

        String password1 = generatePassword();
        password1 = "vyg@123";

        Members defaultUser = Members.builder()
                .name("Tebogo")
                .surname("Kungwane")
                .cellNumber("0711382940")
                .email("tjkungwane@gmail.com")
                .gender(Gender.MALE)
                .isActive(true)
                .password(passwordEncoder.encode(password1))
                .role(Role.SENIOR)
                .capturedBy("system-admin")
                .residentialAddress("2669 nicol street")
                .dateCreated(LocalDateTime.now())
                .address(savedAddress)
                .build();

        Members defaultUserSmith = Members.builder()
                .name("Smith")
                .surname("Dlamini")
                .cellNumber("07820759006")
                .email("vincentlebza@gmail.com")
                .gender(Gender.MALE)
                .isActive(true)
                .password(passwordEncoder.encode(password1))
                .role(Role.SENIOR)
                .capturedBy("system-admin")
                .residentialAddress("Omonde View")
                .dateCreated(LocalDateTime.now())
                .address(savedAddress)
                .build();

        memberRepository.save(defaultUserSmith);
        memberRepository.save(defaultUser);

//        emailService.sendWelcomeEmail(defaultUserSmith.getEmail(), defaultUserSmith.getName(), password2);
        emailService.sendWelcomeEmail(defaultUser.getEmail(), defaultUser.getName(), password1);

        log.info("Default user 'Tebogo Kungwane' seeded successfully.");

        seedParkStationMember();
    }

    private void seedParkStationMember() {
        String email = "Mcastro.za96@gmail.com";
        if (memberRepository.findByEmail(email).isPresent()) {
            log.info("Member '{}' already exists, skipping.", email);
            return;
        }

        Address parkStationAddress = addressRepository
                .findByProvinceAndBranch(Province.GAUTENG, Branch.PARK_STATION)
                .orElseThrow(() -> new RuntimeException(
                        "PARK_STATION address not found. Ensure AddressLoader runs first."
                ));

        String password = "vyg@123";
        Members makeCastro = Members.builder()
                .name("Make")
                .surname("Castro")
                .cellNumber("0660060763")
                .email(email)
                .gender(Gender.MALE)
                .isActive(true)
                .password(passwordEncoder.encode(password))
                .role(Role.SENIOR)
                .capturedBy("system-admin")
                .residentialAddress("Johannesburg CBD")
                .dateCreated(LocalDateTime.now())
                .address(parkStationAddress)
                .build();

        memberRepository.save(makeCastro);
        emailService.sendWelcomeEmail(makeCastro.getEmail(), makeCastro.getName(), password);

        log.info("Member 'Make Castro' (PARK_STATION) seeded successfully.");
    }
}
