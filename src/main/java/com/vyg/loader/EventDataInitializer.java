package com.vyg.loader;

import com.vyg.entity.BaseEvent;
import com.vyg.entity.BaseEvent;
import com.vyg.repository.BaseEventRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventDataInitializer implements CommandLineRunner {

    private final BaseEventRepository eventRepository;

    public EventDataInitializer(BaseEventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public void run(String... args) {
        if (eventRepository.count() == 0) {
            List<BaseEvent> events = List.of(
                    new BaseEvent("Monday (Service)", 50),
                    new BaseEvent("Tuesday", 50),
                    new BaseEvent("Wednesday (Extra)", 50),
                    new BaseEvent("Wednesday (Service)", 80),
                    new BaseEvent("Thursday (TOL)", 50),
                    new BaseEvent("Friday (Service)", 80),
                    new BaseEvent("Friday Recruitment", 50),
                    new BaseEvent("Disciples Meeting", 50),
                    new BaseEvent("Saturday Workout", 50),
                    new BaseEvent("Saturday Projects", 50),
                    new BaseEvent("Sunday (7am Service)", 50),
                    new BaseEvent("New Youth Hangout", 100),
                    new BaseEvent("Returning Youth Hangout", 50)
            );

            eventRepository.saveAll(events);
            System.out.println("✅ Default events loaded into the database.");
        } else {
            System.out.println("ℹ️ Events already exist. Skipping initialization.");
        }
    }
}
