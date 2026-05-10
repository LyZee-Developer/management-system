package com.seng.management_system.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.seng.management_system.model.DataRef;

public interface DataRefRepository extends JpaRepository<DataRef, Long>, JpaSpecificationExecutor<DataRef> {

    Optional<DataRef> findByCode(String code);
}
