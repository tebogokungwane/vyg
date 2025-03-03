package com.vyg.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vyg.enumerator.Gender;
import com.vyg.enumerator.Role;
import com.vyg.enumerator.Nation;
import jakarta.persistence.*;
import lombok.*;

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

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String cellNumber;

    @ManyToOne
    @JoinColumn(name = "province_id")
    private Province province;

    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Nation nation;

    private String password;
    private boolean isActive;

    // ✅ Self-referencing relationship: A Member can have a Mentor
    @JsonIgnore // ✅ Prevents infinite recursion
    @ManyToOne
    @JoinColumn(name = "mentor_id")
    private Members mentor;

    @JsonIgnore // ✅ Prevents infinite recursion
    // ✅ A Mentor can have multiple Members under them
    @OneToMany(mappedBy = "mentor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Members> mentees;
}
