package com.seng.management_system.service;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.seng.management_system.DataModel.generateExpense.generateExpense.GenerateExpenseDataModel;
import com.seng.management_system.DataModel.generateExpense.generateExpense.GenerateExpenseFilterDataModel;
import com.seng.management_system.dto.GenerateExpenseDTO;

public interface GenerateExpenseService {
    Page<GenerateExpenseDTO> list(GenerateExpenseFilterDataModel filter,Pageable pageable );
    GenerateExpenseDTO create(GenerateExpenseDataModel model);
    GenerateExpenseDTO update(Long id,GenerateExpenseDataModel model);
}
