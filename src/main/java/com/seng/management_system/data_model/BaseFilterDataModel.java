package com.seng.management_system.data_model;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseFilterDataModel {
    private Long id;
    private String search;
    private Boolean isActivate;
    private Integer page = 1;
    private Integer record = 10;
}
