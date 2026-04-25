package com.seng.management_system.mapper;

import java.util.List;

import com.seng.management_system.dto.BugDto;
import com.seng.management_system.model.Bug;
import com.seng.management_system.model.Developer;

public class BugMapper {
    public static BugDto MapToDto(Bug model){
        BugDto data = new BugDto();
        List<Developer> developer;
        
        developer = model.getDevelopers();
        data.setId(model.getId());
        data.setTitle(model.getTitle());
        data.setDescription(model.getDescription());
        data.setDeveloper(developer);
        data.setComment(model.getComments());
        data.setCreateBy(model.getCreateBy());
        data.setUpdateBy(model.getUpdateBy());
        data.setIsActivate(model.getIsActivate());
        return data;
    }
  
}
