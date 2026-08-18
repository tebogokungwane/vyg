package com.vyg.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vyg.enumerator.Gender;
import com.vyg.enumerator.Role;
import jakarta.persistence.*;
import lombok.*;
import com.vyg.entity.Nations;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(indexes = {
        @Index(name = "idx_members_email", columnList = "email")
})
public class Members {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;

    @Column(unique = true)
    private String email; // ✅ Add this line

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String cellNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")  // foreign key
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Address address;


    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "nation_id", nullable = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Nations nation;


    private String password;
    private boolean isActive;

    private String residentialAddress;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd MMMM yyyy HH:mm")
    private LocalDateTime dateCreated;

    private String capturedBy;

    private Double latitude;
    private Double longitude;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "mentor_id")
    private Members mentor;

    @JsonIgnore
    @OneToMany(mappedBy = "mentor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Members> mentees;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "school_institution_id", nullable = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private SchoolInstitution schoolInstitution;

}
