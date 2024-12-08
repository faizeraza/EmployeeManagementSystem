package com.remiges.remigesdb.dto;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeDTO implements Serializable {

    @NotNull(message = "empid: Employee ID Cannot Be Empty")
    @Pattern(regexp = "^[A-Z0-9]{4}$", message = "empid: Employee ID must be 6 characters long with uppercase letters or digits")
    private String empid;
    @NotNull(message = "fname: First Name cannot be null")
    @Size(min = 2, max = 100, message = "fname: First name must be between 2 and 100 characters")
    private String fname;
    private String fullname;
    @Past(message = "DOB: Date of Birth must be in the past")
    private LocalDate dob;
    private LocalDate doj;
    @Positive(message = "Salary: Salary must be a positive number")
    private Integer salary;
    private String reportsTo;  // Employee ID of the reporting officer
    private String departmentName;  // Instead of department object
    private String rankDesc;  // Instead of rank object
}
