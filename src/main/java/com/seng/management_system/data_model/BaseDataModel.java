package com.seng.management_system.data_model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseDataModel {

    private String createBy;
    private Date createDate;
    private String updatedBy;
    private Date updatedDate;
    private Boolean isActivate;

}
