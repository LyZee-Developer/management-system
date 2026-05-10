package com.seng.management_system.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.seng.management_system.apiResponse.ApiResponse;
import com.seng.management_system.constant.TrackUserAccessConstant;
import com.seng.management_system.data_model.UserLoginDataModel;
import com.seng.management_system.dto.jwt.JwtResponse;
import com.seng.management_system.exception.ApiException;
import com.seng.management_system.model.TrackUserAccess;
import com.seng.management_system.model.UserLogin;
import com.seng.management_system.repository.TrackUserAccessRepository;
import com.seng.management_system.repository.UserLoginRepository;
import com.seng.management_system.service.UserLoginService;
import com.seng.management_system.service.jwt.JwtService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserLoginService userLoginService;

    @Autowired
    private UserLoginRepository userLoginRepository;

    @Autowired
    private TrackUserAccessRepository trackUserAccessRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDataModel request) {
        try {
            // ✅ Authenticate (this already validates username + password)
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            // ✅ Load user AFTER authentication success
            UserLogin userLogin = userLoginRepository
                    .findByUsernameAndIsActivate(request.getUsername(), true)
                    .orElseThrow(() -> new ApiException("Username not found!"));

            // ✅ Track access
            TrackUserAccess userAccess = new TrackUserAccess();
            List<TrackUserAccess> userAc
                    = trackUserAccessRepository.findByUserLoginIdOrderByIdDesc(userLogin.getId());

            if (CollectionUtils.isEmpty(userAc)
                    || !TrackUserAccessConstant.OPEN.equals(userAc.get(0).getType())) {
                userAccess.setType(TrackUserAccessConstant.OPEN);
                userAccess.setUserLogin(userLogin);
                userAccess.setDate(LocalDateTime.now());
                trackUserAccessRepository.save(userAccess);
            }

            // ✅ Generate token
            JwtResponse response = jwtService.generateToken(request.getUsername());

            return ResponseEntity.ok(ApiResponse.success(response));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Invalid username or password");
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(404).body("User not found");
        } catch (Exception e) {
            e.printStackTrace(); // 👈 debug
            return ResponseEntity.status(500).body("Server error");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> Register(@RequestBody UserLoginDataModel request) {
        long Id = userLoginService.create(request);
        return ResponseEntity.ok(ApiResponse.success(Id));
    }

}
