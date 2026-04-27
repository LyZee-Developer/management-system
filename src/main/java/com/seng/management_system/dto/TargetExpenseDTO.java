package com.seng.management_system.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TargetExpenseDTO {
    private Long id;
    private String nameKh;
    private String nameEn;
    private String description;
}
