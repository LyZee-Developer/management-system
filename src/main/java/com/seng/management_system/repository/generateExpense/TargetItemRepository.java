package com.seng.management_system.repository.generateExpense;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.seng.management_system.model.generateExpense.TargetItem;

public interface TargetItemRepository extends JpaRepository<TargetItem, Long>,JpaSpecificationExecutor<TargetItem> {
    
}
