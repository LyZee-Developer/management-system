package com.seng.management_system.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.seng.management_system.data_model.generateExpense.target_expense.TargetExpenseDataModel;
import com.seng.management_system.data_model.generateExpense.target_expense.TargetExpenseFilterDataModel;
import com.seng.management_system.dto.TargetExpenseDTO;

public interface TargetExpenseService {

    Page<TargetExpenseDTO> list(TargetExpenseFilterDataModel filter, Pageable pageable);

    TargetExpenseDTO create(TargetExpenseDataModel model);

    TargetExpenseDTO update(Long id, TargetExpenseDataModel model);
}
