package com.remiges.remigesdb.dto;

import lombok.Data;

@Data
public class UpdateContributionRequest {

    private String departmentName;
    private String employeeId;
    private int count = 0; // Defaults to 0 if not provided
}
