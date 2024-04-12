package com.ProjX.projxpersitance.entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "ProjectGeneral")
@Table(name = "project_general")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectGeneral extends BasicEntity{

    @Column(name = "project_name")
    private String projectName;

    @OneToMany(mappedBy = "projectGeneral",  fetch = FetchType.EAGER)
    Set<SprintPeriod> sprintPeriods = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "project_user",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> users = new HashSet<>();
}
