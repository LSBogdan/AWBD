package com.ProjX.projxpersitance.dtos;


import jakarta.persistence.Column;
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
public class IssueTopicInfo {

    private String id;
    private String title;
    private String description;
    private String createdBy;
    private String type;
    private String urgency;
    private Long estimantion;
    private String status;
    private String assignedTo;
    private String reporterId;
    private Set<IssueTopicChangeInfo> issueTopicChanges = new HashSet<>();
}
