package com.ProjX.projxcore.apis;

import com.ProjX.projxcore.services.ProjectGeneralService;
import com.ProjX.projxpersitance.dtos.ProjectGeneralCreationInfo;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class ProjectGeneralResourceTest {

    private MockMvc mockMvc;

    @Mock
    private ProjectGeneralService projectGeneralService;

    @InjectMocks
    private ProjectGeneralResource projectGeneralResource;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(projectGeneralResource).build();
    }

    @Test
    void testCreateProjectGeneral() throws Exception {
        ProjectGeneralCreationInfo creationInfo = new ProjectGeneralCreationInfo();
        String id = "123";
        given(projectGeneralService.createProjectGeneral(any(ProjectGeneralCreationInfo.class))).willReturn(id);
        given(projectGeneralService.getProjectGeneralInfoById(id)).willReturn(new ProjectGeneralCreationInfo());

        mockMvc.perform(post("/api/v1/projectGeneral")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(creationInfo)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists());
    }

    @Test
    void testGetProjectGeneralInfoById() throws Exception {
        String id = "123";
        given(projectGeneralService.getProjectGeneralInfoById(id)).willReturn(new ProjectGeneralCreationInfo());

        mockMvc.perform(get("/api/v1/projectGeneral/getById/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists());
    }

    @Test
    void testUpdateProjectGeneral() throws Exception {
        String id = "123";
        ProjectGeneralCreationInfo updatedInfo = new ProjectGeneralCreationInfo();
        given(projectGeneralService.updateProjectGeneral(eq(id), any(ProjectGeneralCreationInfo.class))).willReturn(updatedInfo);

        mockMvc.perform(put("/api/v1/projectGeneral/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatedInfo)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists());
    }

    @Test
    void testDeleteProjectGeneral() throws Exception {
        String id = "123";

        mockMvc.perform(delete("/api/v1/projectGeneral/delete/{id}", id))
                .andExpect(status().isOk());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
