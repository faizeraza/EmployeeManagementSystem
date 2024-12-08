package com.remiges.remigesdb.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.remiges.remigesdb.models.Employee;
import com.remiges.remigesdb.models.EmployeeShadow;

@Component
public class EmployeeShadowMapper {

    private final ModelMapper modelMapper;

    public EmployeeShadowMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public EmployeeShadow copyToEmployeeShadow(Employee employee) {
        return modelMapper.map(employee, EmployeeShadow.class);
    }
}
