package com.ProjX.projxcore.services;

import com.ProjX.projxpersitance.exceptions.CustomErrorHandler;
import com.ProjX.projxpersitance.exceptions.ExceptionEnum;
import com.ProjX.projxpersitance.dtos.IssueTopicCreation;
import com.ProjX.projxpersitance.dtos.IssueTopicInfo;
import com.ProjX.projxpersitance.entitys.IssueTopic;
import com.ProjX.projxpersitance.entitys.IssueTopicDetails;
import com.ProjX.projxpersitance.mappers.IssueTopicDetailsMapper;
import com.ProjX.projxpersitance.mappers.IssueTopicMapper;
import com.ProjX.projxpersitance.repository.IssueTopicDetailsJPARepository;
import com.ProjX.projxpersitance.repository.IssueTopicJPARepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class IssueTopicService {

    private final IssueTopicJPARepository issueTopicJPARepository;
    private final IssueTopicDetailsJPARepository issueTopicDetailsJPARepository;
    private final IssueTopicMapper issueTopicMapper;
    private final IssueTopicDetailsMapper issueTopicDetailsMapper;
    private static final Logger logger = LoggerFactory.getLogger(IssueTopicService.class);


    public String createIssueTopic(IssueTopicCreation issueTopicCreation){
        if(ObjectUtils.isEmpty(issueTopicCreation) )
        {
            throw new CustomErrorHandler(ExceptionEnum.EMPTY_FIELD);}
        if(!creationFieldValidation(issueTopicCreation))
        {
            throw new CustomErrorHandler(ExceptionEnum.INVALID_FIELD);}
        IssueTopic issueTopic = issueTopicMapper.toEntity(issueTopicCreation);
        IssueTopicDetails issueTopicDetails = issueTopicDetailsMapper.toEntity(issueTopicCreation);
        issueTopic.setIssueTopicDetails(issueTopicDetails);
        issueTopicDetails.setIssueTopic(issueTopic);
        return issueTopicJPARepository.save(issueTopic).getId();
    }

    public IssueTopicInfo getIssueTopicInfo(String id){
        if(!StringUtils.hasText(id)){
            logger.error("Object Not Found!");
            throw new CustomErrorHandler(ExceptionEnum.OBJECT_NOT_FOUND);
        }
        Optional<IssueTopic> issueTopic = issueTopicJPARepository.findById(id);
        if(issueTopic.isEmpty() && ObjectUtils.isEmpty(issueTopic)){
            logger.error("Object Not Found!");
            throw new CustomErrorHandler(ExceptionEnum.OBJECT_NOT_FOUND);
        }


        return issueTopicMapper.toDto(issueTopic.get());
    }

    public Page<IssueTopicInfo> getAllIssueTopics(int pageNo, int pageSize, String sortBy, String sortDir) {
        if(!StringUtils.hasText(sortBy)){
            logger.error("SortBy Value Invalid!");
            throw new CustomErrorHandler(ExceptionEnum.INVALID_FIELD);
        }
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.Direction.fromString(sortDir), sortBy);
        Page<IssueTopic> issueTopics = issueTopicJPARepository.findAll(pageable);

        return issueTopics.map(issueTopicMapper::toDto);
    }

    boolean creationFieldValidation(IssueTopicCreation issueTopicCreation){
        if(StringUtils.hasText(issueTopicCreation.getTitle()) && StringUtils.hasText(issueTopicCreation.getType()) &&
        StringUtils.hasText(issueTopicCreation.getUrgency())){
            return true;
        }
        return false;
    }

}
