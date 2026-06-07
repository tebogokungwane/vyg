package com.vyg.loader;

import com.vyg.entity.Project;
import com.vyg.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectDataInitializer implements ApplicationRunner {

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (projectRepository.count() == 0) {
            List<String> defaults = List.of("Sports", "University", "Art & Culture", "Media");
            defaults.forEach(name -> {
                Project p = new Project();
                p.setName(name);
                projectRepository.save(p);
            });
            System.out.println("✅ Default projects seeded");
        }
    }
}