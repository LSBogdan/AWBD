package com.ProjX.projxpersitance.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SprintPeriodCreation {
    private String title;
    private LocalDate startDate;
    private LocalDate endingDate;
    private Boolean endedStatus;


}

