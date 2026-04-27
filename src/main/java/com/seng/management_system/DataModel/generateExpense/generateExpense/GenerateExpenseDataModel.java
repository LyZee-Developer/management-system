package com.seng.management_system.DataModel.generateExpense.generateExpense;

import java.sql.Date;
import java.util.List;

import com.seng.management_system.DataModel.BaseDataModel;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenerateExpenseDataModel extends BaseDataModel{
    private Long id;
    private Date create;
    private Boolean isCurrencyKH;
    private Boolean isActivate;
    private Double amount;
    @NotEmpty(message="required target items.")
    private List<TargetItem> targetItems;


    @Getter
    @Setter
    public static class TargetItem{
        private Long id;
        private Long targetItemId;
        private Double percent;
    }
}
