package org.led.spring.springboot_11_3.web;

import io.swagger.annotations.Api;
import org.led.spring.springboot_11_3.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app")
@Api("SpringBoot test")
public class AppController {
    @Autowired
    private AppService appService;

    @RequestMapping(value="/aoptest", method= RequestMethod.GET)
    public double changeAccuracy() {
        appService.setPrice(5.44567);
        appService.setPrice2(6.8656);
        appService.setPrice3(7.44223);
        appService.setPrice4(8.345843);
        return appService.getPrice();
    }
}
