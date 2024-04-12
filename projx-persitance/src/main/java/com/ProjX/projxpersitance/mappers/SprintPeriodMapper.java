package com.ProjX.projxpersitance.mappers;

import com.ProjX.projxpersitance.dtos.SprintPeriodCreation;
import com.ProjX.projxpersitance.dtos.SprintPeriodInfo;
import com.ProjX.projxpersitance.entitys.SprintPeriod;
import com.ProjX.projxpersitance.mappers.customMapppers.CustomMappersImpl;
import com.ProjX.projxpersitance.mappers.customMapppers.ObjectToString;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = {CustomMappersImpl.class}

)
public interface SprintPeriodMapper {


    SprintPeriod toEntity(SprintPeriodCreation sprintPeriodCreation);

    SprintPeriod toEntity(SprintPeriodInfo sprintPeriodInfo);


    @Mapping(source = "issueTopics", target = "issueTopic", qualifiedBy = ObjectToString.class)
    SprintPeriodInfo toDto(SprintPeriod sprintPeriod);
}
