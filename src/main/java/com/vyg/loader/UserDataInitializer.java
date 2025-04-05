package com.vyg.loader;


import com.vyg.entity.Members;
import com.vyg.enumerator.Gender;
import com.vyg.enumerator.Role;
import com.vyg.repository.MemberRepository;
import com.vyg.repository.NationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;





@Component
public class UserDataInitializer implements CommandLineRunner {

    @Autowired
    private MemberRepository memberRepository;

    @Override
    public void run(String... args) throws Exception {


        if(memberRepository.count() == 0) {

            Members saveDefaultUser = Members.builder()
                    .name("Tebogo")
                    .surname("Kungwane")
                    .cellNumber("0711382940")
                    .email("kungwane@gmail.com")
                    .gender(Gender.MALE)
                    .isActive(true)
                    .password("vyg@123")
                    .role(Role.SENIOR)
                    .capturedBy("system-admin")
                    .residentialAddress("2669 nicol street")
                    .dateCreated(LocalDateTime.now())
                    .build();

            memberRepository.save(saveDefaultUser);
        }
        else {
            System.out.println("default user already added");
        }


    }
}
