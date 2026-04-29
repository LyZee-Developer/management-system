package com.seng.management_system.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.seng.management_system.DataModel.UserLoginDataModel;
import com.seng.management_system.DataModel.UserLoginFilterDataModel;
import com.seng.management_system.dto.UserWithAccessDTO;

public interface  UserLoginService {
    String create(UserLoginDataModel model);
    boolean isLoginSuccess(String username,String password);
    boolean isLogout(String username);
    boolean disabledUser(Long id);
    Page<UserWithAccessDTO> getUserAccessSystem(UserLoginFilterDataModel filter,Pageable pageable);
}
