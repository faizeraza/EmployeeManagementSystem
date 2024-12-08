package com.remiges.remigesdb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.remiges.remigesdb.models.Departments;
import com.remiges.remigesdb.services.DepartmentsService;

public class DepartmentController {

    @Autowired
    DepartmentsService departmentsService;

    @PostMapping("/department/add")
    public ResponseEntity<String> addDepartments(@RequestBody List<Departments> departments) {
        departmentsService.addDepartment(departments);
        return ResponseEntity.status(HttpStatus.CREATED).body("Created!!");
    }

}
