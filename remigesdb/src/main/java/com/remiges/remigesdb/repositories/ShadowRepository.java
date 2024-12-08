package com.remiges.remigesdb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.remiges.remigesdb.models.EmployeeShadow;

public interface ShadowRepository extends JpaRepository<EmployeeShadow, Long> {

}
