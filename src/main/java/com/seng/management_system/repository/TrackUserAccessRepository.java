package com.seng.management_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.seng.management_system.model.TrackUserAccess;

public interface TrackUserAccessRepository extends JpaRepository<TrackUserAccess, Long>, JpaSpecificationExecutor<TrackUserAccess>{

    List<TrackUserAccess> findByUserLoginIdOrderByIdDesc(Long userLogin);
}
