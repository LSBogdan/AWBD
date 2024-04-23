package com.ProjX.projxcore.apis;

import com.ProjX.projxcore.services.IssueTopicChangesService;
import com.ProjX.projxpersitance.dtos.IssueTopicChangeCreation;
import com.ProjX.projxpersitance.dtos.IssueTopicChangeInfo;

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

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class ChangesResourceTest {

    private MockMvc mockMvc;

    @Mock
    private IssueTopicChangesService issueTopicChangesService;

    @InjectMocks
    private ChangesResource changesResource;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(changesResource).build();
    }

    @Test
    void testChangeIssue() throws Exception {
        IssueTopicChangeCreation changeCreation = new IssueTopicChangeCreation();

        mockMvc.perform(post("/api/v1/changes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(changeCreation)))
                .andExpect(status().isCreated());
    }

    @Test
    void testGetIssueTopicInfoById() throws Exception {
        String id = "testId";
        List<IssueTopicChangeInfo> changeInfoList = List.of(new IssueTopicChangeInfo());
        given(issueTopicChangesService.changesIssueTopic(id)).willReturn(changeInfoList);

        mockMvc.perform(get("/api/v1/changes/issueTopicChanges/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0]").exists());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

