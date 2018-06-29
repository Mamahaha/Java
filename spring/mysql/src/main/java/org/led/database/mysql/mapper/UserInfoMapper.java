package org.led.database.mysql.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.led.database.mysql.entity.UserInfo;

@Mapper
public interface UserInfoMapper {
    List<UserInfo> getUsers();
}
