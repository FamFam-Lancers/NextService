package com.example.dbtest.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dbtest.domain.entity.UserInfo;

public interface UserInfoRepository extends JpaRepository<UserInfo, String> {
	
	UserInfo findByEmail(String email);

	UserInfo findByTempkey(String tempkey);

}
