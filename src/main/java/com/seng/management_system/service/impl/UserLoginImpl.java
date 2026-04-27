package com.seng.management_system.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.seng.management_system.DataModel.UserLoginDataModel;
import com.seng.management_system.exception.ApiException;
import com.seng.management_system.model.UserLogin;
import com.seng.management_system.repository.UserLoginRepository;
import com.seng.management_system.service.UserLoginService;
@Service
public class UserLoginImpl implements UserLoginService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserLoginRepository userLoginRepository;

    @Override
    public String create(UserLoginDataModel model){
        UserLogin user = new UserLogin();
        user.setPassword(passwordEncoder.encode(model.getPassword()));
        user.setUsername(model.getUsername());
        user.setIsActivate(Boolean.TRUE);
        user.setAttempt(0);
        user.setSignInDate(LocalDateTime.now());
        userLoginRepository.save(user);
        return "register successfully 🎉🎉";
    }

    @Override
    public boolean isLoginSuccess(String username,String password){
        UserLogin userLogin = userLoginRepository.findByUsernameAndIsActivate(username,Boolean.TRUE).orElseThrow(()-> new ApiException("username not found!"));
        return passwordEncoder.matches(password, userLogin.getPassword());
    }

    @Override
    public boolean disabledUser(Long id){
        UserLogin userLogin = userLoginRepository.findById(id).orElseThrow(()-> new ApiException("username not found!"));
        userLogin.setIsActivate(Boolean.FALSE);
        userLoginRepository.save(userLogin);
        return true;
    }

}
