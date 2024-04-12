package com.ProjX.projxpersitance.entitys;

import com.ProjX.projxpersitance.enums.IssueTopicStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity(name = "IssueTopicDetails")
@Table(name = "issue_topic_details")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IssueTopicDetails extends BasicEntity{

    @Column(name = "estimation")
    private Long estimantion;

    @Column(name = "assigned_to")
    private String assignedTo;

    @Column(name = "reporter")
    private String reporterId;

    @Enumerated(EnumType.STRING)
    @Column(name = "issue_status")
    private IssueTopicStatusEnum status;

    @OneToOne
    @JoinColumn(name = "issue_topic_id")
    private IssueTopic issueTopic;

    @OneToMany(mappedBy = "issueTopicDetails")
    Set<IssueTopicChanges> issueTopicChanges = new HashSet<>();

}
