package com.seng.management_system.data_model.generateExpense.target_expense;

import com.seng.management_system.data_model.BaseDataModel;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TargetExpenseDataModel extends BaseDataModel {

    private Long id;
    @NotEmpty(message = "nameKh is required")
    private String nameKh;
    private String nameEn;
    private String description;
    private Boolean isActivate;
}
