package com.seng.management_system.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.seng.management_system.DataModel.UserLoginDataModel;
import com.seng.management_system.DataModel.UserLoginFilterDataModel;
import com.seng.management_system.constant.TrackUserAccessConstant;
import com.seng.management_system.dto.UserWithAccessDTO;
import com.seng.management_system.exception.ApiException;
import com.seng.management_system.mapper.UserLoginMapper;
import com.seng.management_system.model.TrackUserAccess;
import com.seng.management_system.model.UserLogin;
import com.seng.management_system.repository.TrackUserAccessRepository;
import com.seng.management_system.repository.UserLoginRepository;
import com.seng.management_system.service.UserLoginService;
import com.seng.management_system.specification.UserLoginSpecification;
@Service
public class UserLoginImpl implements UserLoginService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserLoginRepository userLoginRepository;

    @Autowired
    private TrackUserAccessRepository trackUserAccessRepository;

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
        boolean isCorrect = passwordEncoder.matches(password, userLogin.getPassword());
        if(isCorrect){
            TrackUserAccess userAccess = new TrackUserAccess();
            List<TrackUserAccess> userAc = trackUserAccessRepository.findByUserLoginIdOrderByIdDesc(userLogin.getId());
            if(CollectionUtils.isEmpty(userAc)){
                userAccess.setType(TrackUserAccessConstant.OPEN);
            }else{
                String Status = userAc.getFirst().getType();
                if(Status.equals(TrackUserAccessConstant.OPEN)){
                    throw new ApiException("Currently, User is online!");
                }
                userAccess.setType(TrackUserAccessConstant.OPEN);
            }
            userAccess.setUserLogin(userLogin);
            userAccess.setDate(LocalDateTime.now());
            trackUserAccessRepository.save(userAccess);
        }
        return isCorrect;
    }

    @Override
    public boolean isLogout(String username){
        UserLogin userLogin = userLoginRepository.findByUsernameAndIsActivate(username,Boolean.TRUE).orElseThrow(()-> new ApiException("username not found!"));
        TrackUserAccess userAccess = new TrackUserAccess();
        userAccess.setType(TrackUserAccessConstant.CLOSE);
        userAccess.setDate(LocalDateTime.now());
        userAccess.setUserLogin(userLogin);
        trackUserAccessRepository.save(userAccess);
        return true;
    }

    @Override
    public boolean disabledUser(Long id){
        UserLogin userLogin = userLoginRepository.findById(id).orElseThrow(()-> new ApiException("username not found!"));
        userLogin.setIsActivate(Boolean.FALSE);
        userLoginRepository.save(userLogin);
        return true;
    }

    @Override
    public Page<UserWithAccessDTO> getUserAccessSystem(UserLoginFilterDataModel filter ,Pageable pageable){
        Specification<UserLogin> spec =  UserLoginSpecification.buildFilter(filter);
        Page<UserLogin> data = userLoginRepository.findAll(spec,pageable);
        return data.map(UserLoginMapper::MapToDto);
    }

}
