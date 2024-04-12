package com.ProjX.projxpersitance.enums;

import lombok.Getter;

@Getter
public enum IssueTopicTypeEnum {

    ISSUE("Issue"),
    BUG("Bug"),
    HOTFIX("Hot-fix"),
    FEATURE("Feature"),
    RELEASE("Release");

    private String name;

    IssueTopicTypeEnum(String name) {
        this.name = name;
    }
}
