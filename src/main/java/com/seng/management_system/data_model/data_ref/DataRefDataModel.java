package com.seng.management_system.data_model.data_ref;

import com.seng.management_system.data_model.BaseDataModel;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DataRefDataModel extends BaseDataModel {

    private Long id;
    private String parentCode;

    @NotBlank(message = "code is required!")
    private String code;

    @NotBlank(message = "name is required!")
    private String name;

    @NotBlank(message = "enName is required!")
    private String enName;

    private String description;

    private String enDescription;

    private String value;

}
