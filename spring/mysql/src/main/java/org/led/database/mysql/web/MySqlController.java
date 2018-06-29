package org.led.database.mysql.web;

import io.swagger.annotations.Api;
import java.util.List;
import org.led.database.mysql.entity.Broker;
import org.led.database.mysql.entity.Mac;
import org.led.database.mysql.entity.UserInfo;
import org.led.database.mysql.service.BrokerService;
import org.led.database.mysql.service.MacService;
import org.led.database.mysql.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mysql")
@Api("MySql Operation API")
public class MySqlController {

    @Autowired
    private BrokerService brokerService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private MacService macService;

    @RequestMapping(value="/getBrokers", method= RequestMethod.GET, produces="application/json;charset=UTF-8")
    public List<Broker> getBrokers(@RequestParam("category") String category, @RequestParam("type") String type) {
        List<Broker> result = brokerService.getBrokers(category, type);
        for (Broker broker: result) {
            System.out.println("broker: " + broker.toString());
        }
        return result;
    }

    @RequestMapping(value="/getUsers", method= RequestMethod.GET, produces="application/json;charset=UTF-8")
    public List<UserInfo> getUsers() {
        return userInfoService.getUsers();
    }

    @RequestMapping(value="/getMacs", method= RequestMethod.GET, produces="application/json;charset=UTF-8")
    public List<Mac> getMacsByUser(@RequestParam("user") String user) {
        return macService.getMacsbyUser(user);
    }
}
