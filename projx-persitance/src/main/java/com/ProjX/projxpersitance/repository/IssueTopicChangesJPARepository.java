package com.ProjX.projxpersitance.repository;

import com.ProjX.projxpersitance.entitys.IssueTopic;
import com.ProjX.projxpersitance.entitys.IssueTopicChanges;
import com.ProjX.projxpersitance.entitys.IssueTopicDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueTopicChangesJPARepository extends JpaRepository<IssueTopicChanges,String> {

    List<IssueTopicChanges> findIssueTopicChangesByIssueTopic(IssueTopic issueTopic);
    List<IssueTopicChanges> findIssueTopicChangesByIssueTopicDetails(IssueTopicDetails issueTopicDetails);
}
