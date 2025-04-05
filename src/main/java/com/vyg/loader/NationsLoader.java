package com.vyg.loader;

import com.vyg.entity.Nations;
import com.vyg.repository.NationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NationsLoader implements CommandLineRunner {

    @Autowired
    private NationsRepository nationsRepository;



    @Override
    public void run(String... args) {
        List<String> nationList = List.of("Explosion", "Flawless", "Impact", "Invincible", "Revolution", "Unbeatable");

        if(nationsRepository.count() == 0){
            List<Nations> nationEntities = nationList.stream()
                    .map(name -> new Nations(null, name))
                    .collect(Collectors.toList());

            nationsRepository.saveAll(nationEntities);

            System.out.println("✔ Nations seeded.");
        }else{
            System.out.println("Skip Nation loader already loaded ....");
        }


    }
}
