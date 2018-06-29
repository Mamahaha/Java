package org.led.database.mysql.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.led.database.mysql.entity.Broker;


@Mapper
public interface BrokerMapper {
    List<Broker> getBrokers(@Param("category") String category,
        @Param("type") String type);
}
