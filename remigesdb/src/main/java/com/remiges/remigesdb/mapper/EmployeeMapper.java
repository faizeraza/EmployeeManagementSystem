package com.remiges.remigesdb.mapper;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.remiges.remigesdb.dto.EmployeeDTO;
import com.remiges.remigesdb.models.Departments;
import com.remiges.remigesdb.models.Employee;
import com.remiges.remigesdb.models.Rank;
import com.remiges.remigesdb.repositories.DepartmentRepository;
import com.remiges.remigesdb.repositories.EmployeeRepository;
import com.remiges.remigesdb.repositories.RankRepository;

@Component
public class EmployeeMapper {

    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    RankRepository rankRepository;
    @Autowired
    EmployeeRepository employeeRepository;

    public EmployeeDTO toDTO(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setEmpid(employee.getEmpid());
        dto.setFname(employee.getFname());
        dto.setFullname(employee.getFullname());
        dto.setDob(employee.getDob());
        dto.setDoj(employee.getDoj());
        dto.setSalary(employee.getSalary());
        dto.setReportsTo(employee.getReportsTo() != null ? employee.getReportsTo().getFullname() : null);
        dto.setDepartmentName(employee.getDepartment() != null ? employee.getDepartment().getDeptname() : null);
        dto.setRankDesc(employee.getRank() != null ? employee.getRank().getRankdesc() : null);
        return dto;
    }

    public Employee toEntity(EmployeeDTO dto) {
        Employee employee = new Employee();
        employee.setEmpid(dto.getEmpid());
        employee.setFname(dto.getFname());
        employee.setFullname(dto.getFullname());
        employee.setDob(dto.getDob());
        employee.setDoj(dto.getDoj());
        employee.setSalary(dto.getSalary());
        if (dto.getDepartmentName() != null) {
            employee.setDepartment(departmentRepository.findByDeptname(dto.getDepartmentName()).get(0));
        }
        if (dto.getRankDesc() != null) {
            employee.setRank(rankRepository.findByRankdesc(dto.getRankDesc()).get(0));
        }
        if (dto.getReportsTo() != null) {
            employee.setReportsTo(employeeRepository.findByEmpid(dto.getReportsTo()).get(0));
        }
        List<Employee> e = employeeRepository.findByEmpid(dto.getEmpid());
        if (!e.isEmpty()) {
            employee.setCreatedat(e.get(0).getCreatedat());
            employee.setUpdatedat(e.get(0).getUpdatedat());
        }
        return employee;
    }

    public Employee updateEmployee(String employeeId, EmployeeDTO dto) {
        Employee employee = employeeRepository.findByEmpid(employeeId).get(0);
        employee.setEmpid(dto.getEmpid());
        employee.setFname(dto.getFname());
        employee.setFullname(dto.getFullname());
        employee.setDob(dto.getDob());
        employee.setDoj(dto.getDoj());
        employee.setSalary(dto.getSalary());
        if (dto.getReportsTo() != null) {
            Employee emp = employeeRepository.findByEmpid(dto.getReportsTo()).get(0);
            employee.setReportsTo(emp);
        }
        if (dto.getRankDesc() != null) {
            Rank rank = rankRepository.findByRankdesc(dto.getRankDesc()).get(0);
            employee.setRank(rank);
        }
        if (dto.getDepartmentName() != null) {
            Departments department = departmentRepository.findByDeptname(dto.getDepartmentName()).get(0);
            employee.setDepartment(department);
        }
        employee.setUpdatedat(LocalDateTime.now());
        System.out.println(employee.toString());
        return employeeRepository.save(employee);
    }
}
