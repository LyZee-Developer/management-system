package com.seng.management_system.service.impl;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.seng.management_system.constant.GlobalHelper;
import com.seng.management_system.constant.TrackUserAccessConstant;
import com.seng.management_system.data_model.UserLoginDataModel;
import com.seng.management_system.data_model.UserLoginFilterDataModel;
import com.seng.management_system.dto.UserWithAccessDTO;
import com.seng.management_system.exception.ApiException;
import com.seng.management_system.mapper.UserLoginMapper;
import com.seng.management_system.model.TrackUserAccess;
import com.seng.management_system.model.UserInfo;
import com.seng.management_system.model.UserLogin;
import com.seng.management_system.repository.TrackUserAccessRepository;
import com.seng.management_system.repository.UserInfoRepository;
import com.seng.management_system.repository.UserLoginRepository;
import com.seng.management_system.service.UserLoginService;
import com.seng.management_system.specification.UserLoginSpecification;

import jakarta.transaction.Transactional;

@Service
public class UserLoginImpl implements UserLoginService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserLoginRepository userLoginRepository;

    @Autowired
    private TrackUserAccessRepository trackUserAccessRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Override
    @Transactional
    public Long create(UserLoginDataModel model) {
        UserLogin user = new UserLogin();
        user.setPassword(passwordEncoder.encode(model.getPassword()));
        user.setUsername(model.getUsername());
        user.setIsActivate(Boolean.TRUE);
        user.setAttempt(0);
        user.setSignInDate(LocalDateTime.now());
        userLoginRepository.save(user);

        addNewUserInfo(user, model.getUserInfo());

        return user.getId();
    }

    @Override
    public Long isLoginSuccess(String username, String password) {
        UserLogin userLogin = userLoginRepository.findByUsernameAndIsActivate(username, Boolean.TRUE).orElseThrow(() -> new ApiException("username not found!"));
        boolean isCorrect = passwordEncoder.matches(password, userLogin.getPassword());
        if (isCorrect) {
            TrackUserAccess userAccess = new TrackUserAccess();
            List<TrackUserAccess> userAc = trackUserAccessRepository.findByUserLoginIdOrderByIdDesc(userLogin.getId());
            if (CollectionUtils.isEmpty(userAc)) {
                userAccess.setType(TrackUserAccessConstant.OPEN);
            } else {
                String Status = userAc.getFirst().getType();
                if (!Status.equals(TrackUserAccessConstant.OPEN)) {
                    userAccess.setType(TrackUserAccessConstant.OPEN);
                }
            }
            userAccess.setUserLogin(userLogin);
            userAccess.setDate(LocalDateTime.now());
            trackUserAccessRepository.save(userAccess);
        }
        Long id = Optional.ofNullable(userLogin.getId()).orElse(0L);
        return isCorrect ? id : 0L;
    }

    @Override
    public boolean isLogout(String username) {
        UserLogin userLogin = userLoginRepository.findByUsernameAndIsActivate(username, Boolean.TRUE).orElseThrow(() -> new ApiException("username not found!"));
        TrackUserAccess userAccess = new TrackUserAccess();
        userAccess.setType(TrackUserAccessConstant.CLOSE);
        userAccess.setDate(LocalDateTime.now());
        userAccess.setUserLogin(userLogin);
        trackUserAccessRepository.save(userAccess);
        return true;
    }

    @Override
    public boolean disabledUser(Long id) {
        UserLogin userLogin = userLoginRepository.findById(id).orElseThrow(() -> new ApiException("username not found!"));
        userLogin.setIsActivate(Boolean.FALSE);
        userLoginRepository.save(userLogin);
        return true;
    }

    @Override
    public Page<UserWithAccessDTO> getUserAccessSystem(UserLoginFilterDataModel filter, Pageable pageable) {
        Specification<UserLogin> spec = UserLoginSpecification.buildFilter(filter);
        Page<UserLogin> data = userLoginRepository.findAll(spec, pageable);
        return data.map(s -> {
            UserInfo user = userInfoRepository.findByUserLoginId(s.getId());
            return UserLoginMapper.MapToDto(s, user);
        });
    }

    @Override
    public UserInfo getUserLogin(Long userLoginId) {
        UserInfo userInfo = userInfoRepository.findByUserLoginId(userLoginId);

        if (ObjectUtils.isEmpty(userInfo)) {
            throw new ApiException("User info not found!");
        }

        if (!userInfo.getIsActivate()) {
            throw new ApiException("User info account have been disabled!");
        }

        return userInfo;
    }

    private void addNewUserInfo(UserLogin userLogin, UserLoginDataModel.UserInfo user) {
        UserInfo userInfo = new UserInfo();

        userInfo.setEmail(user.getEmail());
        userInfo.setGender(user.getGender());
        userInfo.setName(user.getName());
        userInfo.setHex(user.getHex());
        userInfo.setPhone(user.getPhone());
        userInfo.setIsActivate(Boolean.TRUE);
        userInfo.setCreateBy(GlobalHelper.ADMIN);
        userInfo.setCreateDate(new Date());
        userInfo.setUserLogin(userLogin);

        userInfoRepository.save(userInfo);

    }

}
