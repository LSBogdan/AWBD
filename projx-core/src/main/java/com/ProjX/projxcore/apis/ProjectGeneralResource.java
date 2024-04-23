package com.ProjX.projxcore.apis;

import com.ProjX.projxcore.services.ProjectGeneralService;
import com.ProjX.projxpersitance.dtos.AddUserToProject;
import com.ProjX.projxpersitance.dtos.ProjectGeneralCreationInfo;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.net.URI;
import java.net.URISyntaxException;
@RestController
@RequestMapping(value = "/api/v1/projectGeneral", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
public class ProjectGeneralResource {

    private ProjectGeneralService projectGeneralService;

    @PostMapping
    public ResponseEntity<ProjectGeneralCreationInfo> createProjectGeneral(@RequestBody ProjectGeneralCreationInfo projectGeneralCreationInfo) throws URISyntaxException {
        String id = projectGeneralService.createProjectGeneral(projectGeneralCreationInfo);
        return ResponseEntity.created(new URI(id)).body(projectGeneralService.getProjectGeneralInfoById(id));
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<ProjectGeneralCreationInfo> getProjectGeneralInfoById(@PathVariable String id) {
        ProjectGeneralCreationInfo projectGeneralCreationInfo = projectGeneralService.getProjectGeneralInfoById(id);
        return ResponseEntity.ok(projectGeneralCreationInfo);
    }

    @GetMapping("/getAll")
    public ResponseEntity<Page<ProjectGeneralCreationInfo>> getAllProjectGenerals(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "5") int size,
            @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy,
            @RequestParam(name = "sortDir", required = false, defaultValue = "asc") String sortDir) {
        return ResponseEntity.ok()
                .body(projectGeneralService.getAllProjectGenerals(page, size, sortBy, sortDir));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProjectGeneralCreationInfo> updateProjectGeneral(@PathVariable String id, @RequestBody ProjectGeneralCreationInfo projectGeneralCreationInfo) {
        ProjectGeneralCreationInfo updatedInfo = projectGeneralService.updateProjectGeneral(id, projectGeneralCreationInfo);
        return ResponseEntity.ok(updatedInfo);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProjectGenral(@PathVariable String id){
         projectGeneralService.deleteProjectGeneral(id);
        return ResponseEntity.ok().body(null);
    }

    @PatchMapping("/addUser")
    public ResponseEntity<Void> addUserToProject(@RequestBody AddUserToProject addUserToProject){
        projectGeneralService.addUserToProject(addUserToProject);
        return ResponseEntity.ok().body(null);
    }
}

