package com.seng.management_system.mapper.generateExpense;

import com.seng.management_system.dto.TargetExpenseDTO;
import com.seng.management_system.model.generateExpense.TargetExpense;

public class TargetExpenseMapper {

    public static TargetExpenseDTO toDTO(TargetExpense entity){
        TargetExpenseDTO dto = new TargetExpenseDTO();
        dto.setDescription(entity.getDescription());
        dto.setNameEn(entity.getNameEn());
        dto.setNameKh(entity.getNameKh());
        return dto;
    }

}
