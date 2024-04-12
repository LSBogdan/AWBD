package com.ProjX.projxpersitance.dtos;

import com.ProjX.projxpersitance.enums.IssueTopicStatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class IssueTopicCreation {
    private String title;
    private String description;
    private String type;
    private String urgency;
    private String sprintPeriod;
    private String clonedAfter;
    private Long estimantion;
    private String status;
    private String reporterId;
    private String assignedTo;



}

