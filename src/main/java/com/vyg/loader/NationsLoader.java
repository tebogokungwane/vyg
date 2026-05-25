package com.vyg.loader;

import com.vyg.entity.Nations;
import com.vyg.repository.NationsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@Order(2)
@RequiredArgsConstructor
public class NationsLoader implements CommandLineRunner {

    private final NationsRepository nationsRepository;

    @Override
    public void run(String... args) {
        if (nationsRepository.count() > 0) {
            log.info("Nations already loaded, skipping.");
            return;
        }

        List<Nations> nations = List.of(
                "Explosion", "Flawless", "Impact",
                "Invincible", "Revolution", "Unbeatable"
        ).stream().map(Nations::new).toList();

        nationsRepository.saveAll(nations);
        log.info("Nations seeded.");
    }
}
