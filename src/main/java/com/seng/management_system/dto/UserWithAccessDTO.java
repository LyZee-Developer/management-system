package com.seng.management_system.dto;

import com.seng.management_system.model.TrackUserAccess;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserWithAccessDTO {

    private String username;
    private String colorName;
    private TrackUserAccess lastAccess;
}
