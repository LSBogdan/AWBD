package com.ProjX.projxpersitance.mappers;

import com.ProjX.projxpersitance.dtos.ProjectGeneralCreationInfo;
import com.ProjX.projxpersitance.entitys.ProjectGeneral;
import com.ProjX.projxpersitance.mappers.customMapppers.CustomMappersImpl;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = {SprintPeriodMapper.class}

)
public interface ProjectGeneralMapper {

//    @Mapping(target = "sprints", source = "sprintPeriods")
    ProjectGeneralCreationInfo toDto(ProjectGeneral projectGeneral);

    ProjectGeneral toEntity(ProjectGeneralCreationInfo projectGeneralCreationInfo);
}
