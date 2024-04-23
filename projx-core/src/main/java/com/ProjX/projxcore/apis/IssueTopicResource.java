package com.ProjX.projxcore.apis;

import com.ProjX.projxcore.services.IssueTopicService;
import com.ProjX.projxpersitance.dtos.IssueTopicChangeCreation;
import com.ProjX.projxpersitance.dtos.IssueTopicCreation;
import com.ProjX.projxpersitance.dtos.IssueTopicInfo;
import jakarta.annotation.security.DeclareRoles;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/api/v1/issueTopic", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
public class IssueTopicResource {


    private  IssueTopicService issueTopicService;


    @PostMapping("")
    public ResponseEntity<IssueTopicInfo> createIssueTopic(@RequestBody IssueTopicCreation issueTopicCreation) throws Exception{

        String id= issueTopicService.createIssueTopic(issueTopicCreation);
        return ResponseEntity.created(new URI(id)).body(issueTopicService.getIssueTopicInfo(id));
    }


    @GetMapping("/getById/{id}")
    public ResponseEntity<IssueTopicInfo> getIssueTopicInfoById(@PathVariable("id") String id){
        return ResponseEntity.ok()
                .body(issueTopicService.getIssueTopicInfo(id));
    }

    @GetMapping("/getAll")
    public ResponseEntity<Page<IssueTopicInfo>> getIssueTopics(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "5") int size,
            @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy,
            @RequestParam(name = "sortDir", required = false, defaultValue = "asc") String sortDir) {
        return ResponseEntity.ok()
                .body(issueTopicService.getAllIssueTopics(page, size, sortBy, sortDir));
    }

}
