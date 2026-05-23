package com.seng.management_system.dto.jwt;

import java.util.Date;

import com.seng.management_system.model.UserInfo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {

    private String token;
    private String username;
    private Date expiredAt;
    private String type;
    private UserInfo userInfo;
}
