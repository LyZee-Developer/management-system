package com.seng.management_system.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DeveloperDto extends BaseDto{
    private Long id;
    private String nameEn;
    private String nameKh;
    private Boolean isMale;
    private String position;
}
