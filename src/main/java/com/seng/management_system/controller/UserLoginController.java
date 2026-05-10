package com.seng.management_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seng.management_system.apiResponse.ApiResponse;
import com.seng.management_system.constant.ApiPath;
import com.seng.management_system.data_model.UserLoginDataModel;
import com.seng.management_system.data_model.UserLoginFilterDataModel;
import com.seng.management_system.service.UserLoginService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping(ApiPath.USER_LOGIN)
public class UserLoginController {

    @Autowired
    private UserLoginService userLoginService;

    @PostMapping("/register")
    public ResponseEntity<Object> create(@Valid @RequestBody UserLoginDataModel model) {
        return ResponseEntity.ok(ApiResponse.success(userLoginService.create(model)));
    }

    @PostMapping("/login")
    public ResponseEntity<Object> isLoginSuccess(@Valid @RequestBody UserLoginDataModel model) {
        return ResponseEntity.ok(ApiResponse.success(userLoginService.isLoginSuccess(model.getUsername(), model.getPassword())));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> disabledUser(@PathVariable @Positive Long id) {
        return ResponseEntity.ok(ApiResponse.success(userLoginService.disabledUser(id)));
    }

    @GetMapping("/logout")
    public ResponseEntity<Object> logout() {
        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        return ResponseEntity.ok(ApiResponse.success(userLoginService.isLogout(username)));
    }

    @PostMapping("/user_online")
    public ResponseEntity<Object> getUserAccessSystem(UserLoginFilterDataModel filter, @PageableDefault(page = 0, size = 10) Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(userLoginService.getUserAccessSystem(filter, pageable)));
    }

    @GetMapping("/user_info/{id}")
    public ResponseEntity<Object> getUserLogin(@Positive @PathVariable(name = "id") long userLoginId) {
        return ResponseEntity.ok(ApiResponse.success(userLoginService.getUserLogin(userLoginId)));
    }
}
