package org.led.database.mysql.service;

import java.util.List;
import org.led.database.mysql.entity.Mac;
import org.led.database.mysql.mapper.MacMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MacService {
    @Autowired
    private MacMapper mapper;

    public List<Mac> getMacsbyUser(String user) {
        return mapper.getMacsbyUser(user);
    }
}
