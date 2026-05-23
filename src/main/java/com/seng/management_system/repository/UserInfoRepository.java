package com.seng.management_system.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.seng.management_system.model.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long>, JpaSpecificationExecutor<UserInfo> {

    UserInfo findByName(String name);

    UserInfo findByUserLoginId(Long id);

    Optional<UserInfo> findByIdAndIsActivate(Long id, Boolean isActivate);
}
