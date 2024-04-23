package com.ProjX.projxcore.apis;

import com.ProjX.projxcore.services.SprintPeriodService;
import com.ProjX.projxpersitance.dtos.SprintPeriodCreation;
import com.ProjX.projxpersitance.dtos.SprintPeriodInfo;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class SprintPeriodResourceTest {

    private MockMvc mockMvc;

    @Mock
    private SprintPeriodService sprintPeriodService;

    @InjectMocks
    private SprintPeriodResource sprintPeriodResource;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(sprintPeriodResource).build();
    }

    @Test
    void testCreateSprintPeriod() throws Exception {
        SprintPeriodCreation sprintPeriodCreation = new SprintPeriodCreation();
        String id = "123";
        given(sprintPeriodService.createSprintPeriod(any(SprintPeriodCreation.class))).willReturn(id);
        given(sprintPeriodService.getSprintPeriodById(id)).willReturn(new SprintPeriodInfo());

        mockMvc.perform(post("/api/v1/sprintPeriod")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(sprintPeriodCreation)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists());
    }

    @Test
    void testGetSprintPeriodInfo() throws Exception {
        String id = "123";
        given(sprintPeriodService.getSprintPeriodById(id)).willReturn(new SprintPeriodInfo());

        mockMvc.perform(get("/api/v1/sprintPeriod/getById/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists());
    }

    @Test
    void testDeleteSprintPeriod() throws Exception {
        String id = "123";

        mockMvc.perform(delete("/api/v1/sprintPeriod/delete/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateSprintPeriod() throws Exception {
        String id = "123";
        SprintPeriodInfo sprintPeriodInfo = new SprintPeriodInfo();
        given(sprintPeriodService.updateSprintPeriodInfo(eq(id), any(SprintPeriodInfo.class))).willReturn(id);
        given(sprintPeriodService.getSprintPeriodById(anyString())).willReturn(sprintPeriodInfo);


        mockMvc.perform(put("/api/v1/sprintPeriod/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(sprintPeriodInfo)))
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
