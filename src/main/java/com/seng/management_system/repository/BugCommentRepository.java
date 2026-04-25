package com.seng.management_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.seng.management_system.model.BugComment;

public interface BugCommentRepository extends JpaRepository<BugComment, Long>, JpaSpecificationExecutor<BugComment>{
    
}
