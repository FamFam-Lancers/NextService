package com.example.dbtest.domain.service;

import com.example.dbtest.domain.entity.UserInfo;

public interface SendVerifyMailService {
    void execute(UserInfo userInfo);
}
