package com.seng.management_system.mapper;

import com.seng.management_system.dto.DataRefDTO;
import com.seng.management_system.model.DataRef;

public class DataRefMapper {
    public static DataRefDTO MapToDTO(DataRef model) {
        DataRefDTO data = new DataRefDTO();
        data.setCode(model.getCode());
        data.setId(model.getId());
        data.setName(model.getName());
        data.setName(model.getName());
        return data;
    }
}
