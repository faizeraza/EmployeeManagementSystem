package com.remiges.remigesdb.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.remiges.remigesdb.mapper.EmployeeShadowMapper;
import com.remiges.remigesdb.models.Employee;
import com.remiges.remigesdb.models.EmployeeShadow;
import com.remiges.remigesdb.repositories.ShadowRepository;

@Service
public class EmployeeShadowService {

    @Autowired
    private ShadowRepository shadowRepository;

    @Autowired
    private EmployeeShadowMapper employeeShadowMapper;

    public void copy(Employee employee) {
        EmployeeShadow shadow = employeeShadowMapper.copyToEmployeeShadow(employee);
        shadowRepository.save(shadow);
    }
}
