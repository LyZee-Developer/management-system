package com.seng.management_system.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DataRefDTO {

    private Long id;

    private String code;

    private String name;

    private List<DataRefDTO> child;
}
