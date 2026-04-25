package com.seng.management_system.service;


import org.springframework.data.domain.Page;
import com.seng.management_system.DataModel.generateExpense.targetExpense.TargetExpenseDataModel;
import com.seng.management_system.DataModel.generateExpense.targetExpense.TargetExpenseFilterDataModel;
import com.seng.management_system.dto.TargetExpenseDTO;

public interface TargetExpenseService {
    Page<TargetExpenseDTO> list(TargetExpenseFilterDataModel filter);
    TargetExpenseDTO create(TargetExpenseDataModel model);
    TargetExpenseDTO update(Long id,TargetExpenseDataModel model);
}
