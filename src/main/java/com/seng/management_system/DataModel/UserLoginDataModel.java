package com.seng.management_system.DataModel;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserLoginDataModel {
    @NotEmpty(message="username is required!")
    private String username;
    @NotEmpty(message="username is required!")
    private String password;
}
