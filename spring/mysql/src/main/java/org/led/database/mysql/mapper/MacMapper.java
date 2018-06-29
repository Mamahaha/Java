package org.led.database.mysql.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.led.database.mysql.entity.Mac;

@Mapper
public interface MacMapper {
    List<Mac> getMacsbyUser(String user);
}
