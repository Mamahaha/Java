package org.led.database.mysql.service;

import java.util.List;
import org.led.database.mysql.entity.Broker;
import org.led.database.mysql.mapper.BrokerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@Service
public class BrokerService {

    @Autowired
    private BrokerMapper mapper;

    public List<Broker> getBrokers(String category, String type) {
        return mapper.getBrokers(category, type);
    }
}
