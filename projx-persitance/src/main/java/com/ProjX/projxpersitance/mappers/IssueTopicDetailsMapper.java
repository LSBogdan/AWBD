package com.ProjX.projxpersitance.mappers;


import com.ProjX.projxpersitance.dtos.IssueTopicCreation;
import com.ProjX.projxpersitance.entitys.IssueTopicDetails;
import com.ProjX.projxpersitance.mappers.customMapppers.CustomMappersImpl;
import com.ProjX.projxpersitance.mappers.customMapppers.StringToEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
        uses = {CustomMappersImpl.class}

)
public interface IssueTopicDetailsMapper {

    @Mapping(source = "status" , target = "status" , qualifiedBy = StringToEnum.class)
    IssueTopicDetails toEntity(IssueTopicCreation issueTopicCreation);
}
