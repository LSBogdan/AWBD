package com.ProjX.projxpersitance.entitys;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "IssueTopicChanges")
@Table(name = "issue_topic_changes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IssueTopicChanges extends BasicEntity{

    @Column(name = "changes_type")
    private String changeType;

    @Column(name = "changes_details" , length = 9000)
    private String changeDetails;

    @Column(name = "old_value" , length = 9000)
    private String oldValue;

    @ManyToOne
    @JoinColumn(name = "issue_topic")
    private IssueTopic issueTopic;

    @ManyToOne
    @JoinColumn(name = "issue_topic_details")
    private IssueTopicDetails issueTopicDetails;

}
