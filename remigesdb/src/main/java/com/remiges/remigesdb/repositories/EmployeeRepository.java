package com.remiges.remigesdb.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.remiges.remigesdb.models.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByEmpid(String empid);

    @Modifying
    @Query("DELETE Employee e WHERE e.empid = ?1")
    void deleteByEmpid(String empid);

    @Query("SELECT e FROM Employee e WHERE LOWER(e.fname) LIKE LOWER(CONCAT('%', :filter, '%'))")
    List<Employee> findByNameContainingIgnoreCase(@Param("filter") String filter);

    List<Employee> findByReportsTo(Employee reportsTo);
}
