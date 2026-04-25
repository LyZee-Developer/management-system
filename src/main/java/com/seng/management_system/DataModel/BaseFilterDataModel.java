package com.seng.management_system.DataModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseFilterDataModel {
    private Long id;
    private String search;
    private Boolean isActivate;
}
