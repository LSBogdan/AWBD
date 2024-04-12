package com.ProjX.projxpersitance.enums;

import lombok.Getter;

@Getter
public enum IssueTopicUrgencyEnum {
    LOW("Low" , "Low priority issue",1),
    MEDIUM("Medium", "Medium priorty issue",2),
    High("High", "High priority issue",3),
    CRITICAL("Critical","This issue is Critical resolve it ASAP",4);


    private String name;
    private String description;
    private int importanceLevel;

    IssueTopicUrgencyEnum(String name, String description, int importanceLevel) {
        this.name = name;
        this.description = description;
        this.importanceLevel = importanceLevel;
    }

}
