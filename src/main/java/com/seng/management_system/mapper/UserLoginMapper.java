package com.seng.management_system.mapper;

import java.util.List;

import com.seng.management_system.dto.UserWithAccessDTO;
import com.seng.management_system.model.TrackUserAccess;
import com.seng.management_system.model.UserLogin;

public class UserLoginMapper {
    public static UserWithAccessDTO MapToDto(UserLogin model){
        UserWithAccessDTO data = new UserWithAccessDTO();
        List<TrackUserAccess> trackUserAccess = model.getTrackUserAccesses();
        if(!trackUserAccess.isEmpty()){
            data.setLastAccess(trackUserAccess.getFirst());
        } 
        data.setUsername(model.getUsername());
        return data;
    }
}
