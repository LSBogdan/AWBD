package com.ProjX.projxcore.apis;

import com.ProjX.projxcore.services.SprintPeriodService;
import com.ProjX.projxpersitance.dtos.IssueTopicCreation;
import com.ProjX.projxpersitance.dtos.IssueTopicInfo;
import com.ProjX.projxpersitance.dtos.SprintPeriodCreation;
import com.ProjX.projxpersitance.dtos.SprintPeriodInfo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.http11.filters.VoidInputFilter;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.net.URI;

@RestController
@RequestMapping(value = "/api/v1/sprintPeriod", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
public class SprintPeriodResource {

    private SprintPeriodService sprintPeriodService;


    @PostMapping("")
    public ResponseEntity<SprintPeriodInfo> createSprintPeriod(@RequestBody SprintPeriodCreation sprintPeriodCreation) throws Exception{
        String id= sprintPeriodService.createSprintPeriod(sprintPeriodCreation);
        return ResponseEntity.created(new URI(id)).body(sprintPeriodService.getSprintPeriodById(id));
    }


    @GetMapping("/getById/{id}")
    public ResponseEntity<SprintPeriodInfo> getSprintPeriodInfo(@PathVariable("id") String id){
        return ResponseEntity.ok()
                .body(sprintPeriodService.getSprintPeriodById(id));
    }

    @GetMapping("/getAll")
    public ResponseEntity<Page<SprintPeriodInfo>> getAllSprintPeriods(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "5") int size,
            @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy,
            @RequestParam(name = "sortDir", required = false, defaultValue = "asc") String sortDir) {
        return ResponseEntity.ok()
                .body(sprintPeriodService.getAllSprintPeriods(page, size, sortBy, sortDir));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSprintPeriod(@PathVariable("id") String id){
        sprintPeriodService.deleteSprintPeriod(id);
        return ResponseEntity.ok().body(null);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<SprintPeriodInfo> updateSprintPeriod(@PathVariable("id") String id, @RequestBody SprintPeriodInfo sprintPeriodInfo){
        String idO = sprintPeriodService.updateSprintPeriodInfo(id,sprintPeriodInfo);
        return ResponseEntity.ok().body(sprintPeriodService.getSprintPeriodById(idO));
    }


}
