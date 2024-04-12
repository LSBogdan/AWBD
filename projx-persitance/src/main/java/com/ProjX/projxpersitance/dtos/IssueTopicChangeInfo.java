package com.ProjX.projxpersitance.dtos;

import com.ProjX.projxpersitance.entitys.IssueTopic;
import com.ProjX.projxpersitance.entitys.IssueTopicDetails;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IssueTopicChangeInfo {

    private String id;
    private String changeType;
    private String changeDetails;
    private String oldValue;

}
