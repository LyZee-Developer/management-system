package com.seng.management_system.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BaseDto {
    private String createBy;
    private String updateBy;
    private Date createDate;
    private Date updateDate;
    private Boolean isActivate;
}
