package com.seng.management_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.seng.management_system.model.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long>, JpaSpecificationExecutor<UserInfo> {

    UserInfo findByName(String name);

    UserInfo findByUserLoginId(Long id);
}
