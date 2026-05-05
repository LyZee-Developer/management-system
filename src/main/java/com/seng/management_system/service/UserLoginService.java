package com.seng.management_system.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.seng.management_system.DataModel.UserLoginDataModel;
import com.seng.management_system.DataModel.UserLoginFilterDataModel;
import com.seng.management_system.dto.UserWithAccessDTO;
import com.seng.management_system.model.UserInfo;

public interface UserLoginService {

    Long create(UserLoginDataModel model);

    Long isLoginSuccess(String username, String password);

    boolean isLogout(String username);

    boolean disabledUser(Long id);

    Page<UserWithAccessDTO> getUserAccessSystem(UserLoginFilterDataModel filter, Pageable pageable);

    UserInfo getUserLogin(Long userLoginId);
}
