package com.ProjX.projxpersitance.repository;

import com.ProjX.projxpersitance.entitys.SprintPeriod;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SprintPeriodJPARepository extends JpaRepository<SprintPeriod,String> {
    Page<SprintPeriod> findAll(Pageable pageable);
}
