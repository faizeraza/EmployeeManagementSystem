package com.remiges.remigesdb.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.remiges.remigesdb.dto.EmployeeDTO;
import com.remiges.remigesdb.mapper.EmployeeMapper;
import com.remiges.remigesdb.models.Employee;
import com.remiges.remigesdb.repositories.EmployeeRepository;
import com.remiges.remigesdb.utils.ExcelUtils;

import jakarta.transaction.Transactional;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    EmployeeMapper employeeMapper;
    @Autowired
    EmployeeShadowService employeeShadowService;

    public void addEmployees(List<EmployeeDTO> emps) {
        for (EmployeeDTO empDto : emps) {
            Employee emp = employeeMapper.toEntity(empDto);
            emp.setCreatedat(LocalDateTime.now());
            emp.setUpdatedat(LocalDateTime.now());
            employeeRepository.save(emp);
        }
    }

    public void addEmployee(EmployeeDTO empDto) {
        Employee emp = employeeMapper.toEntity(empDto);
        // emp.setCreatedat(LocalDateTime.now());
        emp.setUpdatedat(LocalDateTime.now());
        System.out.println("emp:  " + employeeRepository);
        employeeRepository.save(emp);
    }

    @Cacheable(value = "DetailedEmployees")
    public List<EmployeeDTO> getAll() {
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeDTO> employeeDTOs = new ArrayList<>();
        employees.forEach(employee -> employeeDTOs.add(employeeMapper.toDTO(employee)));
        return employeeDTOs;
    }

    @Cacheable(value = "employees")
    public List<EmployeeDTO> employeeArray() {
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeDTO> employeeDTOs = new ArrayList<>();
        employees.forEach(employee -> {
            EmployeeDTO dto = new EmployeeDTO();
            dto.setEmpid(employee.getEmpid());
            dto.setFname(employee.getFname());
            employeeDTOs.add(dto);
        });
        return employeeDTOs;
    }

    @Cacheable(value = "employeesBytes")
    public byte[] employeeExcel() {
        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeDTO> employeeDTOs = new ArrayList<>();
        employees.forEach(employee -> {
            EmployeeDTO employeeData = new EmployeeDTO();
            employeeData.setEmpid(String.valueOf(employee.getEmpid())); // Convert empid to String
            employeeData.setFullname(employee.getFullname());
            employeeData.setFname(employee.getFname());
            employeeDTOs.add(employeeData);
        });
        return ExcelUtils.generateExcelFile(employeeDTOs);
    }

    @Cacheable(value = "employee", key = "#empid")
    public EmployeeDTO findByEmpId(String empid) {
        return employeeMapper.toDTO(employeeRepository.findByEmpid(empid).get(0));
    }

    @Transactional
    @CacheEvict(cacheNames = "employee", key = "#empid", beforeInvocation = true)
    public EmployeeDTO deletByEmpId(String empid) {
        Employee employee = employeeRepository.findByEmpid(empid).get(0);
        List<Employee> team = employeeRepository.findByReportsTo(employee);
        for (Employee emp : team) {
            emp.setReportsTo(emp);
            employeeRepository.save(emp);
        }
        employeeShadowService.copy(employee);
        employeeRepository.deleteByEmpid(empid);
        return employeeMapper.toDTO(employee);
    }

    @CachePut(cacheNames = "employee", key = "#empid")
    public EmployeeDTO updateEmployee(String empid, EmployeeDTO employeeDto) {
        Employee existingEmployee = employeeRepository.findByEmpid(empid).get(0);
        employeeShadowService.copy(existingEmployee);
        if (existingEmployee != null) {
            Employee employee = employeeMapper.toEntity(employeeDto);
            employee.setId(existingEmployee.getId());
            employee.setEmpid(empid);
            existingEmployee = employeeRepository.save(employee);
        }
        return employeeMapper.toDTO(existingEmployee);
    }

    @Cacheable(value = "employee", key = "#filter")
    public List<EmployeeDTO> searchByFilter(String filter) {
        List<Employee> employees = employeeRepository.findByNameContainingIgnoreCase(filter);
        List<EmployeeDTO> employeeDTOs = new ArrayList<>();
        if (filter != null && !filter.isEmpty()) {
            employees.forEach(employee -> employeeDTOs.add(employeeMapper.toDTO(employee)));
            return employeeDTOs;
        } else {
            return this.getAll();
        }
    }

}
