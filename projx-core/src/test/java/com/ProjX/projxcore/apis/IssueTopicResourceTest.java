package com.ProjX.projxcore.apis;

import com.ProjX.projxcore.services.IssueTopicService;
import com.ProjX.projxpersitance.dtos.IssueTopicCreation;
import com.ProjX.projxpersitance.dtos.IssueTopicInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class IssueTopicResourceTest {

    private MockMvc mockMvc;

    @Mock
    private IssueTopicService issueTopicService;

    @InjectMocks
    private IssueTopicResource issueTopicResource;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(issueTopicResource).build();
    }

    @Test
    void testCreateIssueTopic() throws Exception {
        IssueTopicCreation creation = new IssueTopicCreation();
        String id = "123";
        when(issueTopicService.createIssueTopic(any(IssueTopicCreation.class))).thenReturn(id);
        when(issueTopicService.getIssueTopicInfo(id)).thenReturn(new IssueTopicInfo());

        mockMvc.perform(post("/api/v1/issueTopic")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(creation)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists());
    }

    @Test
    void testGetIssueTopicInfoById() throws Exception {
        String id = "123";
        when(issueTopicService.getIssueTopicInfo(id)).thenReturn(new IssueTopicInfo());

        mockMvc.perform(get("/api/v1/issueTopic/getById/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists());
    }
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
