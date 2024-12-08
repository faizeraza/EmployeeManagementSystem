package com.remiges.remigesdb.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.remiges.remigesdb.models.Departments;
import com.remiges.remigesdb.repositories.DepartmentRepository;

@Service
public class DepartmentsService {

    @Autowired
    DepartmentRepository departmentRepository;

    public void addDepartment(List<Departments> dep) {
        departmentRepository.saveAll(dep);
        System.out.println("saved");
    }
}
