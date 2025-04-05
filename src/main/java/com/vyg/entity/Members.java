package com.vyg.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @ManyToOne
    @JoinColumn(name = "address_id")  // foreign key
    private Address address;


    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "nation_id")
    private Nations nation;

    private String password;
    private boolean isActive;

    private String residentialAddress;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd MMMM yyyy HH:mm")
    private LocalDateTime dateCreated;

    private String capturedBy;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "mentor_id")
    private Members mentor;

    @JsonIgnore
    @OneToMany(mappedBy = "mentor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Members> mentees;

}
