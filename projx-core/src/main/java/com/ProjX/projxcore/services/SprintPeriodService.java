package com.ProjX.projxcore.services;


import com.ProjX.projxpersitance.dtos.IssueTopicInfo;
import com.ProjX.projxpersitance.entitys.IssueTopic;
import com.ProjX.projxpersitance.exceptions.CustomErrorHandler;
import com.ProjX.projxpersitance.exceptions.ExceptionEnum;
import com.ProjX.projxpersitance.dtos.SprintPeriodCreation;
import com.ProjX.projxpersitance.dtos.SprintPeriodInfo;
import com.ProjX.projxpersitance.entitys.SprintPeriod;
import com.ProjX.projxpersitance.mappers.IssueTopicMapper;
import com.ProjX.projxpersitance.mappers.SprintPeriodMapper;
import com.ProjX.projxpersitance.repository.IssueTopicJPARepository;
import com.ProjX.projxpersitance.repository.SprintPeriodJPARepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SprintPeriodService {

    private final SprintPeriodJPARepository sprintPeriodJPARepository;
    private final SprintPeriodMapper sprintPeriodMapper;
    private final IssueTopicMapper issueTopicMapper;
    private final IssueTopicJPARepository issueTopicJPARepository;
    private static final Logger logger = LoggerFactory.getLogger(IssueTopicService.class);


    public String createSprintPeriod(SprintPeriodCreation sprintPeriodCreation){

        if(!StringUtils.hasText(sprintPeriodCreation.getTitle())){
            throw new CustomErrorHandler(ExceptionEnum.EMPTY_FIELD);
        }
        SprintPeriod sprintPeriod = sprintPeriodMapper.toEntity(sprintPeriodCreation);
        String id = sprintPeriodJPARepository.save(sprintPeriod).getId();
        if(!StringUtils.hasText(id)){
            logger.error("Object Not created!");
            throw new CustomErrorHandler(ExceptionEnum.OBJECT_NOT_CREATED);
        }
        logger.info("Sprint created succesfully!");
        return id;
    }

    public SprintPeriodInfo getSprintPeriodById(String id){
        if(!StringUtils.hasText(id)){
            logger.error("Id is null!");
            throw new CustomErrorHandler(ExceptionEnum.INVALID_FIELD);
        }
        Optional<SprintPeriod> sprintPeriod = sprintPeriodJPARepository.findById(id);
        if(sprintPeriod.isEmpty()){
            logger.error("Object not found!");
            throw new CustomErrorHandler(ExceptionEnum.OBJECT_NOT_FOUND);
        }
        SprintPeriodInfo sprintPeriodInfo = sprintPeriodMapper.toDto(sprintPeriod.get());
        return sprintPeriodInfo;
    }

    public Page<SprintPeriodInfo> getAllSprintPeriods(int pageNo, int pageSize, String sortBy, String sortDir) {
        if(!StringUtils.hasText(sortBy)){
            logger.error("Sort By is empty!");
            throw new CustomErrorHandler(ExceptionEnum.INVALID_FIELD);
        }
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.Direction.fromString(sortDir), sortBy);
        Page<SprintPeriod> sprintPeriods = sprintPeriodJPARepository.findAll(pageable);

        return sprintPeriods.map(sprintPeriodMapper::toDto);
    }

    public void deleteSprintPeriod(String id){
        if(!StringUtils.hasText(id)){
            logger.error("Id is null!");
            throw new CustomErrorHandler(ExceptionEnum.INVALID_FIELD);
        }
        Optional<SprintPeriod> sprintPeriod = sprintPeriodJPARepository.findById(id);
        if(sprintPeriod.isEmpty()){
            logger.error("Object Not found!");
            throw new CustomErrorHandler(ExceptionEnum.OBJECT_NOT_FOUND);
        }
        sprintPeriodJPARepository.delete(sprintPeriod.get());
    }

    public String updateSprintPeriodInfo(String id, SprintPeriodInfo sprintPeriodInfo){
        if(!StringUtils.hasText(id) || !StringUtils.hasText(sprintPeriodInfo.getId()) || !id.equals(sprintPeriodInfo.getId())){
            logger.error("Id is null!");
            throw new CustomErrorHandler(ExceptionEnum.INVALID_FIELD);
        }
        SprintPeriod sprintPeriod = sprintPeriodMapper.toEntity(sprintPeriodInfo);

        if (!CollectionUtils.isEmpty(sprintPeriodInfo.getIssueTopic())) {
            for (String issueTopicId : sprintPeriodInfo.getIssueTopic()) {
                Optional<IssueTopic> issueTopicOptional = issueTopicJPARepository.findById(issueTopicId);
                if (issueTopicOptional.isPresent()) {
                    IssueTopic issueTopic = issueTopicOptional.get();
                    issueTopic.setSprintPeriod(sprintPeriod);
                }
            }
        }
        logger.info("Sprint Period updated succesfully!");
        return sprintPeriodJPARepository.save(sprintPeriod).getId();
    }
}
