package com.ProjX.projxpersitance.entitys;

import com.ProjX.projxpersitance.enums.IssueTopicTypeEnum;
import com.ProjX.projxpersitance.enums.IssueTopicUrgencyEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "IssueTopic")
@Table(name = "issue_topic")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IssueTopic extends BasicEntity{

    @Column(name = "title", length = 128)
    private String title;

    @Column(name = "description", length = 2048)
    private String description;

    @Column(name = "created_by")
    private String createdBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "issue_type")
    private IssueTopicTypeEnum type;

    @Enumerated(EnumType.STRING)
    @Column(name = "issue_urgency")
    private IssueTopicUrgencyEnum urgency;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "sprint_period_id")
    private SprintPeriod sprintPeriod;

    @OneToMany(mappedBy = "issueTopic", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    Set<IssueTopicChanges> issueTopicChanges = new HashSet<>();

    @OneToMany(mappedBy = "clonedAfter")
    Set<IssueTopic> clonedAfterIssues = new HashSet<>();

    @OneToOne(mappedBy = "issueTopic", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
    private IssueTopicDetails issueTopicDetails;

    @ManyToOne
    @JoinColumn(name = "cloned_after")
    private IssueTopic clonedAfter;




}
