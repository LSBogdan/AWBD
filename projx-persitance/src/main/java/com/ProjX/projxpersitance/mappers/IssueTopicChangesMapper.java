package com.ProjX.projxpersitance.mappers;

import com.ProjX.projxpersitance.dtos.IssueTopicChangeInfo;
import com.ProjX.projxpersitance.entitys.IssueTopicChanges;
import com.ProjX.projxpersitance.mappers.customMapppers.CustomMappersImpl;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring",
        uses = {}

)
public interface IssueTopicChangesMapper {

    IssueTopicChangeInfo toDto(IssueTopicChanges issueTopicChanges);
}
