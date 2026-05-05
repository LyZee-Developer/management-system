package com.seng.management_system.dto.jwt;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JwtResponse {

    private String token;
    private String username;
    private Date expiredAt;
    private String type;
}
