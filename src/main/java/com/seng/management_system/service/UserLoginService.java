package com.seng.management_system.service;

import com.seng.management_system.DataModel.UserLoginDataModel;

public interface  UserLoginService {
    String create(UserLoginDataModel model);
    boolean isLoginSuccess(String username,String password);
    boolean isLogout(String username);
    boolean disabledUser(Long id);
}
