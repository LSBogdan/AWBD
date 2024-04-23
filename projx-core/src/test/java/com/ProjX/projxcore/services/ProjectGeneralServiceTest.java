package com.ProjX.projxcore.services;

import com.ProjX.projxpersitance.dtos.IssueTopicCreation;
import com.ProjX.projxpersitance.dtos.IssueTopicInfo;
import com.ProjX.projxpersitance.dtos.ProjectGeneralCreationInfo;
import com.ProjX.projxpersitance.entitys.IssueTopic;
import com.ProjX.projxpersitance.entitys.IssueTopicDetails;
import com.ProjX.projxpersitance.entitys.ProjectGeneral;
import com.ProjX.projxpersitance.entitys.SprintPeriod;
import com.ProjX.projxpersitance.mappers.IssueTopicDetailsMapper;
import com.ProjX.projxpersitance.mappers.IssueTopicMapper;
import com.ProjX.projxpersitance.mappers.ProjectGeneralMapper;
import com.ProjX.projxpersitance.repository.IssueTopicDetailsJPARepository;
import com.ProjX.projxpersitance.repository.IssueTopicJPARepository;
import com.ProjX.projxpersitance.repository.ProjectGeneralJPARepository;
import com.ProjX.projxpersitance.repository.SprintPeriodJPARepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class ProjectGeneralServiceTest {

    @Mock
    private ProjectGeneralJPARepository projectGeneralJPARepository;

    @Mock
    private SprintPeriodJPARepository sprintPeriodJPARepository;

    @Mock
    private ProjectGeneralMapper projectGeneralMapper;

    @InjectMocks
    private ProjectGeneralService projectGeneralService;

    @Test
    void testCreateProjectGeneral() {
        ProjectGeneralCreationInfo creationInfo = new ProjectGeneralCreationInfo();
        ProjectGeneral mockProjectGeneral = new ProjectGeneral();
        when(projectGeneralMapper.toEntity(any(ProjectGeneralCreationInfo.class))).thenReturn(mockProjectGeneral);
        when(projectGeneralJPARepository.save(any(ProjectGeneral.class))).thenReturn(mockProjectGeneral);
        creationInfo.setProjectName("nume");
        mockProjectGeneral.setId("test");
        String result = projectGeneralService.createProjectGeneral(creationInfo);

        assertNotNull(result);
        verify(projectGeneralJPARepository, times(1)).save(any(ProjectGeneral.class));
    }

    @Test
    void testGetProjectGeneralInfoById() {
        String id = "testId";
        ProjectGeneral mockProjectGeneral = new ProjectGeneral();
        when(projectGeneralJPARepository.findById(id)).thenReturn(Optional.of(mockProjectGeneral));
        when(projectGeneralMapper.toDto(any(ProjectGeneral.class))).thenReturn(new ProjectGeneralCreationInfo());

        ProjectGeneralCreationInfo result = projectGeneralService.getProjectGeneralInfoById(id);

        assertNotNull(result);
        verify(projectGeneralJPARepository, times(1)).findById(id);
    }

    @Test
    void testDeleteProjectGeneral() {
        String id = "testId";
        ProjectGeneral mockProjectGeneral = new ProjectGeneral();
        when(projectGeneralJPARepository.findById(id)).thenReturn(Optional.of(mockProjectGeneral));

        projectGeneralService.deleteProjectGeneral(id);

        verify(projectGeneralJPARepository, times(1)).delete(mockProjectGeneral);
    }

    @Test
    void testUpdateProjectGeneral() {
        String id = "testId";
        ProjectGeneralCreationInfo creationInfo = new ProjectGeneralCreationInfo();
        creationInfo.setId(id);
        ProjectGeneral mockProjectGeneral = new ProjectGeneral();
        mockProjectGeneral.setProjectName("test");
        when(projectGeneralJPARepository.findById(id)).thenReturn(Optional.of(mockProjectGeneral));
        when(projectGeneralMapper.toEntity(any(ProjectGeneralCreationInfo.class))).thenReturn(mockProjectGeneral);
        when(sprintPeriodJPARepository.findAllById(any())).thenReturn(List.of(new SprintPeriod()));
        when(projectGeneralJPARepository.save(any(ProjectGeneral.class))).thenReturn(mockProjectGeneral);
        when(projectGeneralMapper.toDto(any(ProjectGeneral.class))).thenReturn(creationInfo);
        ProjectGeneralCreationInfo result = projectGeneralService.updateProjectGeneral(id, creationInfo);

        assertNotNull(result);
        verify(projectGeneralJPARepository, times(1)).save(mockProjectGeneral);
    }
}