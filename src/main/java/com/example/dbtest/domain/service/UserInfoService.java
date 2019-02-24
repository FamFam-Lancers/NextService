package com.example.dbtest.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.dbtest.domain.entity.UserInfo;
import com.example.dbtest.domain.repositories.UserInfoRepository;

@Service("UserInfoService")
public class UserInfoService implements UserDetailsService {
	
	private final UserInfoRepository userInfoRepository;
	
	private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserInfoService(UserInfoRepository userInfoRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userInfoRepository = userInfoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		System.out.println(email);//user@test到着

		if(email == null || email.isEmpty()) {
			throw new UsernameNotFoundException("Username is empty");
		}
		
		UserInfo userInfo = userInfoRepository.findByEmail(email);

		if(userInfo == null) {
			throw new UsernameNotFoundException("User not found for email:" + email);
		}

		return userInfo;
	}

	public UserInfo findByTempkey(String tempkey) {
    	return userInfoRepository.findByTempkey(tempkey);
	}
	
	@Transactional
	public void save(UserInfo userInfo) {
		String hushPass = passwordEncoder.encode(userInfo.getPassword());
		userInfo.setPassword(hushPass);
		userInfoRepository.save(userInfo);
	}

	@Transactional
	public void setEnabled(UserInfo userInfo) {
		userInfo.setEnabled(true);
		userInfoRepository.save(userInfo);
	}

}
