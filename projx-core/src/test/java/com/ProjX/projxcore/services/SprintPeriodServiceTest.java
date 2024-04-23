package com.ProjX.projxcore.services;

import com.ProjX.projxpersitance.dtos.SprintPeriodCreation;
import com.ProjX.projxpersitance.dtos.SprintPeriodInfo;
import com.ProjX.projxpersitance.entitys.IssueTopic;
import com.ProjX.projxpersitance.entitys.SprintPeriod;
import com.ProjX.projxpersitance.mappers.IssueTopicMapper;
import com.ProjX.projxpersitance.mappers.SprintPeriodMapper;
import com.ProjX.projxpersitance.repository.IssueTopicJPARepository;
import com.ProjX.projxpersitance.repository.SprintPeriodJPARepository;
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
public class SprintPeriodServiceTest {

    @Mock
    private SprintPeriodJPARepository sprintPeriodJPARepository;

    @Mock
    private SprintPeriodMapper sprintPeriodMapper;

    @Mock
    private IssueTopicMapper issueTopicMapper;

    @Mock
    private IssueTopicJPARepository issueTopicJPARepository;

    @InjectMocks
    private SprintPeriodService sprintPeriodService;


    @Test
    void testCreateSprintPeriod() {
        SprintPeriodCreation sprintPeriodCreation = new SprintPeriodCreation();
        SprintPeriod mockSprintPeriod = new SprintPeriod();
        mockSprintPeriod.setId("test");
        sprintPeriodCreation.setTitle("title");
        when(sprintPeriodMapper.toEntity(any(SprintPeriodCreation.class))).thenReturn(mockSprintPeriod);
        when(sprintPeriodJPARepository.save(any(SprintPeriod.class))).thenReturn(mockSprintPeriod);

        String result = sprintPeriodService.createSprintPeriod(sprintPeriodCreation);

        assertNotNull(result);
        verify(sprintPeriodJPARepository, times(1)).save(any(SprintPeriod.class));
    }

    @Test
    void testGetSprintPeriodById() {
        String id = "testId";
        SprintPeriod mockSprintPeriod = new SprintPeriod();
        when(sprintPeriodJPARepository.findById(id)).thenReturn(Optional.of(mockSprintPeriod));
        when(sprintPeriodMapper.toDto(any(SprintPeriod.class))).thenReturn(new SprintPeriodInfo());

        SprintPeriodInfo result = sprintPeriodService.getSprintPeriodById(id);

        assertNotNull(result);
        verify(sprintPeriodJPARepository, times(1)).findById(id);
    }

    @Test
    void testDeleteSprintPeriod() {
        String id = "testId";
        SprintPeriod mockSprintPeriod = new SprintPeriod();
        when(sprintPeriodJPARepository.findById(id)).thenReturn(Optional.of(mockSprintPeriod));

        sprintPeriodService.deleteSprintPeriod(id);

        verify(sprintPeriodJPARepository, times(1)).delete(mockSprintPeriod);
    }

    @Test
    void testUpdateSprintPeriodInfo() {
        String id = "testId";
        SprintPeriodInfo sprintPeriodInfo = new SprintPeriodInfo();
        sprintPeriodInfo.setId("testId");
        SprintPeriod mockSprintPeriod = new SprintPeriod();
        mockSprintPeriod.setId("test");
        when(sprintPeriodJPARepository.findById(id)).thenReturn(Optional.of(mockSprintPeriod));
        when(sprintPeriodMapper.toEntity(any(SprintPeriodInfo.class))).thenReturn(mockSprintPeriod);
        when(issueTopicJPARepository.findById(anyString())).thenReturn(Optional.of(new IssueTopic()));
        when(sprintPeriodJPARepository.save(any(SprintPeriod.class))).thenReturn(mockSprintPeriod);
        when(sprintPeriodMapper.toDto(any(SprintPeriod.class))).thenReturn(sprintPeriodInfo);
        String result = sprintPeriodService.updateSprintPeriodInfo(id, sprintPeriodInfo);

        assertNotNull(result);
        verify(sprintPeriodJPARepository, times(1)).save(mockSprintPeriod);
    }

}

