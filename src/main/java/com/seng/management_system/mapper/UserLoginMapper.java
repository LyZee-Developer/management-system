package com.seng.management_system.mapper;

import java.util.List;

import com.seng.management_system.dto.UserWithAccessDTO;
import com.seng.management_system.model.TrackUserAccess;
import com.seng.management_system.model.UserInfo;
import com.seng.management_system.model.UserLogin;

public class UserLoginMapper {

    public static UserWithAccessDTO MapToDto(UserLogin model, UserInfo user) {
        UserWithAccessDTO data = new UserWithAccessDTO();
        List<TrackUserAccess> trackUserAccess = model.getTrackUserAccesses();
        if (!trackUserAccess.isEmpty()) {
            data.setLastAccess(trackUserAccess.getFirst());
        }
        data.setUserId(user.getId());
        data.setColorName(user.getHex());
        data.setUsername(user.getName());
        return data;
    }
}
