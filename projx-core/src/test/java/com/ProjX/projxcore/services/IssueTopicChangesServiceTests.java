package com.ProjX.projxcore.services;

import com.ProjX.projxpersitance.dtos.IssueTopicChangeCreation;
import com.ProjX.projxpersitance.dtos.IssueTopicChangeInfo;
import com.ProjX.projxpersitance.entitys.IssueTopic;
import com.ProjX.projxpersitance.entitys.IssueTopicChanges;
import com.ProjX.projxpersitance.entitys.IssueTopicDetails;
import com.ProjX.projxpersitance.mappers.IssueTopicChangesMapper;
import com.ProjX.projxpersitance.repository.IssueTopicChangesJPARepository;
import com.ProjX.projxpersitance.repository.IssueTopicDetailsJPARepository;
import com.ProjX.projxpersitance.repository.IssueTopicJPARepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.Field;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class IssueTopicChangesServiceTests {

        @Mock
        private IssueTopicJPARepository issueTopicJPARepository;

        @Mock
        private IssueTopicDetailsJPARepository issueTopicDetailsJPARepository;

        @Mock
        private IssueTopicChangesJPARepository issueTopicChangesJPARepository;

        @Mock
        private IssueTopicChangesMapper issueTopicChangesMapper;

        @InjectMocks
        private IssueTopicChangesService issueTopicChangesService;


        @Test
        void testChangeIssueTopic() throws Exception {
            IssueTopicChangeCreation creation = new IssueTopicChangeCreation();
            creation.setIssueTopicId("test");
            creation.setIssueTopicDetailsId("test");
            Map<String,String> map = new HashMap<>();
            map.put("title","newVal");
            creation.setChanges(map);
            IssueTopic issueTopic = new IssueTopic();
            issueTopic.setTitle("old");

            when(issueTopicJPARepository.findById(anyString())).thenReturn(Optional.of(issueTopic));
            when(issueTopicDetailsJPARepository.findById(anyString())).thenReturn(Optional.of(new IssueTopicDetails()));

            issueTopicChangesService.changeIssueTopic(creation);

            verify(issueTopicJPARepository, times(1)).save(any(IssueTopic.class));
            verify(issueTopicDetailsJPARepository, times(1)).save(any(IssueTopicDetails.class));
            verify(issueTopicChangesJPARepository, atLeastOnce()).save(any(IssueTopicChanges.class));
        }

        @Test
        void testGetOldDataValue() {
            String issueTopicId = "testId";
            String issueTopicDetailsId = "testId";
            String fieldName = "testField";
            IssueTopic mockIssueTopic = new IssueTopic();
            IssueTopicDetails mockIssueTopicDetails = new IssueTopicDetails();

            when(issueTopicJPARepository.findById(issueTopicId)).thenReturn(Optional.of(mockIssueTopic));
            when(issueTopicDetailsJPARepository.findById(issueTopicDetailsId)).thenReturn(Optional.of(mockIssueTopicDetails));

            String result = issueTopicChangesService.getOldDataValue(issueTopicId, issueTopicDetailsId, fieldName);

            assertNotNull(result);
        }

        @Test
        void testIsFieldInIssueTopic() {
            String fieldName = "title";
            String id = "testId";
            IssueTopic mockIssueTopic = new IssueTopic();
            when(issueTopicJPARepository.findById(id)).thenReturn(Optional.of(mockIssueTopic));

            IssueTopic result = issueTopicChangesService.isFieldInIssueTopic(fieldName, id);

            assertNotNull(result);
        }

        @Test
        void testIsFieldInIssueTopicDetails() {
            String fieldName = "estimantion";
            String id = "testId";
            IssueTopicDetails mockIssueTopicDetails = new IssueTopicDetails();
            when(issueTopicDetailsJPARepository.findById(id)).thenReturn(Optional.of(mockIssueTopicDetails));

            IssueTopicDetails result = issueTopicChangesService.isFieldInIssueTopicDetails(fieldName, id);

            assertNotNull(result);
        }

        @Test
        void testSetFieldValue() throws NoSuchFieldException, IllegalAccessException {
            IssueTopic mockIssueTopic = new IssueTopic();
            String fieldName = "title";
            String newValue = "test";

            IssueTopicChangesService.setFieldValue(mockIssueTopic, fieldName, newValue);

            Field field = IssueTopic.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            assertEquals(newValue, field.get(mockIssueTopic));
        }

        @Test
        void testChangesIssueTopic() {
            String id = "testId";
            when(issueTopicJPARepository.findById(id)).thenReturn(Optional.of(new IssueTopic()));
            when(issueTopicDetailsJPARepository.findById(id)).thenReturn(Optional.of(new IssueTopicDetails()));
            IssueTopicChanges issueTopicChanges = new IssueTopicChanges();
            when(issueTopicChangesJPARepository.findIssueTopicChangesByIssueTopic(any(IssueTopic.class)))
                    .thenReturn(List.of(issueTopicChanges));
            when(issueTopicChangesJPARepository.findIssueTopicChangesByIssueTopicDetails(any(IssueTopicDetails.class)))
                    .thenReturn(List.of(issueTopicChanges));

            List<IssueTopicChangeInfo> result = issueTopicChangesService.changesIssueTopic(id);

            assertNotNull(result);
            assertFalse(result.isEmpty());
        }
    }
