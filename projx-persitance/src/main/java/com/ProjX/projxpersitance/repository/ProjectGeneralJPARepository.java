package com.ProjX.projxpersitance.repository;

import com.ProjX.projxpersitance.entitys.ProjectGeneral;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectGeneralJPARepository extends JpaRepository<ProjectGeneral,String> {
    Page<ProjectGeneral> findAll(Pageable pageable);
}
