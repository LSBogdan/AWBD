package com.ProjX.projxcore.services;

import com.ProjX.projxpersitance.dtos.IssueTopicCreation;
import com.ProjX.projxpersitance.dtos.IssueTopicInfo;
import com.ProjX.projxpersitance.entitys.IssueTopic;
import com.ProjX.projxpersitance.entitys.IssueTopicDetails;
import com.ProjX.projxpersitance.mappers.IssueTopicDetailsMapper;
import com.ProjX.projxpersitance.mappers.IssueTopicMapper;
import com.ProjX.projxpersitance.repository.IssueTopicDetailsJPARepository;
import com.ProjX.projxpersitance.repository.IssueTopicJPARepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class IssueTopicServiceTest {

    @Mock
    private IssueTopicJPARepository issueTopicJPARepository;

    @Mock
    private IssueTopicDetailsJPARepository issueTopicDetailsJPARepository;

    @Mock
    private IssueTopicMapper issueTopicMapper;

    @Mock
    private IssueTopicDetailsMapper issueTopicDetailsMapper;

    @InjectMocks
    private IssueTopicService issueTopicService;


    @Test
    void testCreateIssueTopic() {
        IssueTopicCreation creation = new IssueTopicCreation();
        IssueTopic mockIssueTopic = new IssueTopic();
        mockIssueTopic.setId("test");
        when(issueTopicMapper.toEntity(any(IssueTopicCreation.class))).thenReturn(mockIssueTopic);
        when(issueTopicJPARepository.save(any(IssueTopic.class))).thenReturn(mockIssueTopic);
        when(issueTopicDetailsMapper.toEntity(any(IssueTopicCreation.class))).thenReturn(new IssueTopicDetails());
        creation.setTitle("test");
        creation.setType("Bug");
        creation.setUrgency("Medium");
        String result = issueTopicService.createIssueTopic(creation);

        assertNotNull(result);
        verify(issueTopicJPARepository, times(1)).save(any(IssueTopic.class));
    }

    @Test
    void testGetIssueTopicInfo() {
        String id = "testId";
        IssueTopic mockIssueTopic = new IssueTopic();
        when(issueTopicJPARepository.findById(id)).thenReturn(Optional.of(mockIssueTopic));
        when(issueTopicMapper.toDto(any(IssueTopic.class))).thenReturn(new IssueTopicInfo());

        IssueTopicInfo result = issueTopicService.getIssueTopicInfo(id);

        assertNotNull(result);
        verify(issueTopicJPARepository, times(1)).findById(id);
    }

    @Test
    void testCreationFieldValidation() {
        IssueTopicCreation creation = new IssueTopicCreation();
        creation.setTitle("test");
        creation.setType("Bug");
        creation.setUrgency("Medium");

        boolean isValid = issueTopicService.creationFieldValidation(creation);

        assertTrue(isValid);
    }
}