package com.ProjX.projxpersitance.entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "SprintPeriod")
@Table(name = "sprint_period")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SprintPeriod extends BasicEntity{

    @Column(name = "title")
    private String title;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "ending_date")
    private LocalDate endingDate;

    @Column(name = "endedStatus")
    private Boolean endedStatus;

    @OneToMany(mappedBy = "sprintPeriod" ,cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    Set<IssueTopic> issueTopics = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "project")
    private ProjectGeneral projectGeneral;
}
