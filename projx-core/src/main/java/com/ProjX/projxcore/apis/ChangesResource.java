package com.ProjX.projxcore.apis;

import com.ProjX.projxcore.services.IssueTopicChangesService;
import com.ProjX.projxpersitance.dtos.IssueTopicChangeCreation;
import com.ProjX.projxpersitance.dtos.IssueTopicChangeInfo;
import com.ProjX.projxpersitance.dtos.IssueTopicCreation;
import com.ProjX.projxpersitance.dtos.IssueTopicInfo;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/changes", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
@SecurityRequirement(name = "Bearer Authentication")

public class ChangesResource {

    private IssueTopicChangesService issueTopicChangesService;

    @PostMapping("")
    public ResponseEntity<Void> changeIssue(@RequestBody IssueTopicChangeCreation issueTopicChangeCreation) throws Exception{
        issueTopicChangesService.changeIssueTopic(issueTopicChangeCreation);
        return ResponseEntity.created(new URI("id")).body(null);
    }

    @GetMapping("/issueTopicChanges/{id}")
    public ResponseEntity<List<IssueTopicChangeInfo>> getIssueTopicInfoById(@PathVariable("id") String id){
        return ResponseEntity.ok()
                .body(issueTopicChangesService.changesIssueTopic(id));
    }


}
