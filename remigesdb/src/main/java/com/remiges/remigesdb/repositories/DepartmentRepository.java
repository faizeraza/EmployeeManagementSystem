package com.remiges.remigesdb.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.remiges.remigesdb.models.Departments;

public interface DepartmentRepository extends JpaRepository<Departments, Long> {

    List<Departments> findByDeptname(String deptname);
}
