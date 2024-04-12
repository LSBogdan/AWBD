package com.ProjX.projxpersitance.repository;

import com.ProjX.projxpersitance.entitys.IssueTopicDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssueTopicDetailsJPARepository extends JpaRepository<IssueTopicDetails,String> {
}
