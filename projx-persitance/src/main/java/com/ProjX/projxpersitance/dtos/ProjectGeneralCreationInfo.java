package com.ProjX.projxpersitance.dtos;

import com.ProjX.projxpersitance.entitys.SprintPeriod;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectGeneralCreationInfo {

    private String id;
    private String projectName;
    Set<SprintPeriodInfo> sprints= new HashSet<>();

}
