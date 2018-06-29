package org.led.database.mysql.service;

import java.util.List;
import org.led.database.mysql.entity.UserInfo;
import org.led.database.mysql.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoService {
    @Autowired
    private UserInfoMapper mapper;

    public List<UserInfo> getUsers() {
        return mapper.getUsers();
    }
}
