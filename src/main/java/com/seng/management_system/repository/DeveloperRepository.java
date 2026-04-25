package com.seng.management_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.seng.management_system.model.Developer;

public interface  DeveloperRepository extends JpaRepository<Developer, Long>, JpaSpecificationExecutor<Developer>{
    
}
