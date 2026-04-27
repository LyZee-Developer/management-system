package com.seng.management_system.mapper.generateExpense;

import java.util.Optional;
import java.util.stream.Collectors;

import com.seng.management_system.dto.GenerateExpenseDTO;
import com.seng.management_system.dto.GenerateExpenseDTO.TargetItemDTO;
import com.seng.management_system.model.generateExpense.GenerateExpense;

public class GenerateExpenseMapper {

    public static GenerateExpenseDTO toDTO(GenerateExpense data){
        GenerateExpenseDTO dto = new GenerateExpenseDTO();
        dto.setAmount(data.getAmount());
        dto.setIsCurrencyKHR(data.getIsKHR());
        dto.setDate(data.getDate());
        dto.setId(data.getId());
        dto.setTargetItem(data.getTargetItems().stream().map(s -> {
            GenerateExpenseDTO.TargetItemDTO subDto = new TargetItemDTO();
            Double getAmount = Optional.ofNullable(data.getAmount()).map(amount-> (amount * (s.getPercent() / 100))).orElse(0.0);
            subDto.setId(s.getId());
            subDto.setPercent(s.getPercent());
            subDto.setTargetExpense(s.getTargetExpense());
            subDto.setGetAmount(getAmount);
            return subDto;
        }).collect(Collectors.toList()));
        return dto;
    }

}
