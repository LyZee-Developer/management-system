package com.seng.management_system.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.seng.management_system.model.UserLogin;

public interface UserLoginRepository extends  JpaRepository<UserLogin, Long> , JpaSpecificationExecutor<UserLogin>{
    List<UserLogin> findByUsernameAndPassword(String username, String password);
    Optional<UserLogin> findByUsername(String name);
    Optional<UserLogin> findByUsernameAndIsActivate(String username,boolean isActivate);
}
