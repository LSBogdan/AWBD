package com.ProjX.projxpersitance.mappers;

import com.ProjX.projxpersitance.dtos.IssueTopicCreation;
import com.ProjX.projxpersitance.dtos.IssueTopicInfo;
import com.ProjX.projxpersitance.entitys.IssueTopic;
import com.ProjX.projxpersitance.mappers.customMapppers.CustomMappersImpl;
import com.ProjX.projxpersitance.mappers.customMapppers.EnumToString;
import com.ProjX.projxpersitance.mappers.customMapppers.StringToEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = {CustomMappersImpl.class}

)
public interface IssueTopicMapper {

    @Mapping(source = "type" , target = "type" , qualifiedBy = StringToEnum.class)
    @Mapping(source = "urgency" , target = "urgency" , qualifiedBy = StringToEnum.class)
    @Mapping(target = "sprintPeriod", ignore = true)
    @Mapping(target = "clonedAfter", ignore = true)
    IssueTopic toEntity(IssueTopicCreation issueTopicCreation);

    @Mapping(source = "type" , target = "type" , qualifiedBy = EnumToString.class)
    @Mapping(source = "urgency" , target = "urgency" , qualifiedBy = EnumToString.class)
    @Mapping(source = "issueTopicDetails.estimantion" , target = "estimantion")
    @Mapping(source = "issueTopicDetails.assignedTo" , target = "assignedTo")
    @Mapping(source = "issueTopicDetails.reporterId" , target = "reporterId")
    @Mapping(source = "issueTopicDetails.status" , target = "status" , qualifiedBy = EnumToString.class)
    IssueTopicInfo toDto(IssueTopic issueTopic);
}
