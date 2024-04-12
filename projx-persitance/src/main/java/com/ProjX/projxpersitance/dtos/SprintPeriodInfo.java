package com.ProjX.projxpersitance.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SprintPeriodInfo {

    private String id;
    private String title;
    private LocalDate startDate;
    private LocalDate endingDate;
    private Boolean endedStatus;

    private HashSet<String> issueTopic = new HashSet<>();
}
