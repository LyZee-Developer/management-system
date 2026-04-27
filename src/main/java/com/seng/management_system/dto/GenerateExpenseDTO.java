package com.seng.management_system.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.seng.management_system.model.generateExpense.TargetExpense;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class GenerateExpenseDTO {
    private Long id;
    private Date date;  
    private Double amount;
    private Boolean isCurrencyKHR;
    private List<TargetItemDTO> targetItem;

    @Getter
    @Setter
    public static class TargetItemDTO{
        private Long id;
        private Double percent;
        @JsonIncludeProperties({"id","nameKh","nameEn","description"})
        private TargetExpense targetExpense;
        private Double getAmount;
    }
}
