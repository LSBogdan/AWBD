package com.ProjX.projxpersitance.repository;

import com.ProjX.projxpersitance.entitys.IssueTopic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssueTopicJPARepository extends JpaRepository<IssueTopic, String> {
    Page<IssueTopic> findAll(Pageable pageable);
}
