package com.seng.management_system.mapper;

import com.seng.management_system.dto.DeveloperDto;
import com.seng.management_system.model.Developer;

public class DeveloperMapper {
    public static DeveloperDto MapToDto(Developer model){
        DeveloperDto data = new DeveloperDto();
        data.setId(model.getId());
        data.setNameEn(model.getNameEn());
        data.setNameKh(model.getNameKh());
        data.setCreateBy(model.getCreateBy());
        data.setUpdateBy(model.getUpdateBy());
        data.setIsActivate(model.getIsActivate());
        data.setPosition(model.getPosition());
        data.setIsMale(model.getIsMale());
        return data;
    }

    public static Developer MapToModel(Developer model){
        Developer data = new Developer();
        data.setId(model.getId());
        data.setNameEn(model.getNameEn());
        data.setNameKh(model.getNameKh());
        data.setCreateBy(model.getCreateBy());
        data.setUpdateBy(model.getUpdateBy());
        data.setIsActivate(model.getIsActivate());
        data.setPosition(model.getPosition());
        data.setIsMale(model.getIsMale());
        return data;
    }
}
