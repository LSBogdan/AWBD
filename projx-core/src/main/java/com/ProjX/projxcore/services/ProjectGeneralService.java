package com.ProjX.projxcore.services;

import com.ProjX.projxpersitance.dtos.AddUserToProject;
import com.ProjX.projxpersitance.dtos.ProjectGeneralCreationInfo;
import com.ProjX.projxpersitance.entitys.ProjectGeneral;
import com.ProjX.projxpersitance.entitys.SprintPeriod;
import com.ProjX.projxpersitance.entitys.User;
import com.ProjX.projxpersitance.exceptions.CustomErrorHandler;
import com.ProjX.projxpersitance.exceptions.ExceptionEnum;
import com.ProjX.projxpersitance.mappers.ProjectGeneralMapper;
import com.ProjX.projxpersitance.repository.ProjectGeneralJPARepository;
import com.ProjX.projxpersitance.repository.SprintPeriodJPARepository;
import com.ProjX.projxpersitance.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class ProjectGeneralService {

    private final ProjectGeneralJPARepository projectGeneralJPARepository;

    private final SprintPeriodJPARepository sprintPeriodJPARepository;

    private final ProjectGeneralMapper projectGeneralMapper;
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(IssueTopicService.class);


    public String createProjectGeneral(ProjectGeneralCreationInfo projectGeneralCreationInfo){
        if(!StringUtils.hasText(projectGeneralCreationInfo.getProjectName())){
            logger.error("Project Name is Null!");
            throw new CustomErrorHandler(ExceptionEnum.EMPTY_FIELD);
        }
        return projectGeneralJPARepository.save(projectGeneralMapper.toEntity(projectGeneralCreationInfo)).getId();
    }

    public ProjectGeneralCreationInfo getProjectGeneralInfoById(String id){
        if(!StringUtils.hasText(id)){
            logger.error("Id is Null!");
            throw new CustomErrorHandler(ExceptionEnum.INVALID_FIELD);
        }
        Optional<ProjectGeneral> projectGeneralCreationInfo = projectGeneralJPARepository.findById(id);
        if(projectGeneralCreationInfo.isEmpty()){
            logger.error("Project not Found!");
            throw new CustomErrorHandler(ExceptionEnum.OBJECT_NOT_FOUND);
        }
        return projectGeneralMapper.toDto(projectGeneralCreationInfo.get());
    }

    public Page<ProjectGeneralCreationInfo> getAllProjectGenerals(int pageNo, int pageSize, String sortBy, String sortDir) {
        if(!StringUtils.hasText(sortBy)){
            logger.error("SortBy is Null!");
            throw new CustomErrorHandler(ExceptionEnum.INVALID_FIELD);
        }
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.Direction.fromString(sortDir), sortBy);
        Page<ProjectGeneral> projectGenerals = projectGeneralJPARepository.findAll(pageable);

        return projectGenerals.map(projectGeneralMapper::toDto);
    }

    public void deleteProjectGeneral(String id){
        if(!StringUtils.hasText(id)){
            logger.error("Id is Null!");
            throw new CustomErrorHandler(ExceptionEnum.INVALID_FIELD);
        }
        Optional<ProjectGeneral> projectGeneral = projectGeneralJPARepository.findById(id);
        if(projectGeneral.isEmpty()){
            logger.error("Project not Found!");
            throw new CustomErrorHandler(ExceptionEnum.OBJECT_NOT_FOUND);
        }
        projectGeneralJPARepository.delete(projectGeneral.get());
    }

    public ProjectGeneralCreationInfo updateProjectGeneral(String id, ProjectGeneralCreationInfo projectGeneralCreationInfo){
        if(!StringUtils.hasText(id) || !projectGeneralCreationInfo.getId().equals(id)){
            logger.error("Id is null!");
            throw new CustomErrorHandler(ExceptionEnum.OBJECT_NOT_FOUND);
        }
        Optional<ProjectGeneral> projectGeneral = projectGeneralJPARepository.findById(id);
        if(projectGeneral.isEmpty()){
            logger.error("Project not Found!");
            throw new CustomErrorHandler(ExceptionEnum.OBJECT_NOT_FOUND);
        }
        ProjectGeneral projectGeneralGet = projectGeneral.get();
        projectGeneralGet.setProjectName(projectGeneralCreationInfo.getProjectName());
        Set<SprintPeriod> sprintPeriods = projectGeneralGet.getSprintPeriods();
        Set<SprintPeriod> sprintPeriodSet = projectGeneralCreationInfo.getSprints().stream().map(x->sprintPeriodJPARepository.findById(x).orElseThrow(()->new CustomErrorHandler(ExceptionEnum.OBJECT_NOT_FOUND))).collect(Collectors.toSet());
        sprintPeriods.addAll(sprintPeriodSet);
        projectGeneralGet.setSprintPeriods(sprintPeriods);
        sprintPeriodSet.forEach(x->{x.setProjectGeneral(projectGeneralGet);sprintPeriodJPARepository.save(x);});
        ProjectGeneral savedProjectGeneral = projectGeneralJPARepository.save(projectGeneral.get());
        ProjectGeneralCreationInfo projectGeneralCreationInfo1 = new ProjectGeneralCreationInfo();
        projectGeneralCreationInfo1.setId(savedProjectGeneral.getId());
        projectGeneralCreationInfo1.setProjectName(savedProjectGeneral.getProjectName());
        projectGeneralCreationInfo1.setSprints(savedProjectGeneral.getSprintPeriods().stream().map(x->x.getId()).collect(Collectors.toSet()));
        return projectGeneralCreationInfo1;
    }

    @Transactional
    public void addUserToProject(AddUserToProject addUserToProject) {
        User user = userRepository.findById(addUserToProject.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        ProjectGeneral project = projectGeneralJPARepository.findById(addUserToProject.getProjectId()).orElseThrow(() -> new RuntimeException("Project not found"));
        project.getUsers().add(user);
        projectGeneralJPARepository.save(project);
    }


}
