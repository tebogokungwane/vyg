package com.vyg.loader;

import com.vyg.entity.VygLearner;
import com.vyg.entity.VygSchool;
import com.vyg.repository.VygLearnerRepository;
import com.vyg.repository.VygSchoolRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@Order(2)
@RequiredArgsConstructor
public class VygSchoolLearnerLoader implements CommandLineRunner {

    private final VygSchoolRepository vygSchoolRepository;
    private final VygLearnerRepository vygLearnerRepository;

    @Override
    public void run(String... args) {
        loadSchools();
        loadLearners();
    }

    private void loadSchools() {
        if (vygSchoolRepository.count() > 0) {
            log.info("VYG schools already exist ({}), skipping seed.", vygSchoolRepository.count());
            return;
        }

        try {
            ClassPathResource resource = new ClassPathResource("db/changelog/data/vyg_school_data.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));

            String header = reader.readLine(); // skip header
            if (header == null) return;

            String line;
            int count = 0;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) continue;

                String[] parts = parseCsvLine(line);
                if (parts.length < 11) continue;

                VygSchool school = new VygSchool();
                school.setSchoolId(parts[0].trim());
                school.setSchoolImage(parts[1].trim());
                school.setSchoolName(parts[2].trim());
                school.setArea(parts[3].trim());
                school.setProvince(parts[4].trim());
                school.setContactTeacherName(parts[5].trim());
                school.setContactTeacherPhone(parts[6].trim());
                school.setContactTeacherEmail(parts[7].trim());
                school.setGradesInvolved(parts[8].trim());
                school.setActiveProgrammes(parts[9].trim());
                school.setStatus(parts[10].trim());

                vygSchoolRepository.save(school);
                count++;
            }
            reader.close();
            log.info("Seeded {} VYG schools from CSV.", count);
        } catch (Exception e) {
            log.error("Failed to seed VYG schools: {}", e.getMessage());
        }
    }

    private void loadLearners() {
        if (vygLearnerRepository.count() > 0) {
            log.info("VYG learners already exist ({}), skipping seed.", vygLearnerRepository.count());
            return;
        }

        try {
            ClassPathResource resource = new ClassPathResource("db/changelog/data/vyg_learner_data.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));

            String header = reader.readLine(); // skip header
            if (header == null) return;

            String line;
            int count = 0;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) continue;

                String[] parts = line.split(",", -1);
                if (parts.length < 8) continue;

                String schoolId = parts[1].trim();
                VygSchool school = vygSchoolRepository.findById(schoolId).orElse(null);
                if (school == null) {
                    log.warn("School {} not found for learner {}, skipping.", schoolId, parts[0].trim());
                    continue;
                }

                VygLearner learner = new VygLearner();
                learner.setLearnerId(parts[0].trim());
                learner.setFullName(parts[2].trim());
                learner.setGrade(parts[3].trim());
                learner.setGender(parts[4].trim());
                learner.setProgrammeInterests(parts[5].trim());
                learner.setNeedsMentor(parts[6].trim().equalsIgnoreCase("true"));
                learner.setStatus(parts[7].trim());
                learner.setSchool(school);

                vygLearnerRepository.save(learner);
                count++;
            }
            reader.close();
            log.info("Seeded {} VYG learners from CSV.", count);
        } catch (Exception e) {
            log.error("Failed to seed VYG learners: {}", e.getMessage());
        }
    }

    /**
     * Parses a CSV line respecting quoted fields (e.g. "Grade 8 , Grade 9").
     */
    private String[] parseCsvLine(String line) {
        java.util.List<String> fields = new java.util.ArrayList<>();
        boolean inQuotes = false;
        StringBuilder current = new StringBuilder();

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                fields.add(current.toString());
                current = new StringBuilder();
            } else {
                current.append(c);
            }
        }
        fields.add(current.toString());

        return fields.toArray(new String[0]);
    }
}
