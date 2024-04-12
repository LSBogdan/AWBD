package com.ProjX.projxpersitance.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IssueTopicChangeCreation {

    private String changedBy;
    private Map changes =new HashMap<String,String>();

    private String issueTopicId;
    private String issueTopicDetailsId;
}
