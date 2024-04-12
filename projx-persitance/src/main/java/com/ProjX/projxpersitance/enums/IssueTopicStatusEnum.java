package com.ProjX.projxpersitance.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum IssueTopicStatusEnum {
    CLOSED("Closed",Arrays.asList()),
    FINISHED("Finished",Arrays.asList(CLOSED)),
    DEV_IN_PROGRESS("Development in progress",Arrays.asList(FINISHED)),
    NOT_STARTED("Not Started",Arrays.asList(DEV_IN_PROGRESS,CLOSED)),
    REOPENED("Reopend",Arrays.asList(NOT_STARTED,DEV_IN_PROGRESS,CLOSED,FINISHED));

    private String name;
private List<IssueTopicStatusEnum> nextStatus;

    IssueTopicStatusEnum(String name, List<IssueTopicStatusEnum> nextStatus) {
        this.name = name;
        this.nextStatus = nextStatus;
    }
}