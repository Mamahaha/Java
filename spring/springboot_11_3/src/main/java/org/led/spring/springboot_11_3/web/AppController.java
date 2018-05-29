package org.led.spring.springboot_11_3.web;

import io.swagger.annotations.Api;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.led.spring.springboot_11_3.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Api("SpringBoot test")
public class AppController {
    @Autowired
    private AppService appService;

    @GetMapping(value="/aoptest")
    public double changeAccuracy() {
        appService.setPrice(5.44567);
        appService.setPrice2(6.8656);
        appService.setPrice3(7.44223);
        appService.setPrice4(8.345843);
        double[] prices = {5.44567, 6.2131, 5.658983, 9.23632};
        appService.setPrices(prices);
        appService.printPrices();
        return appService.getPrice();
    }

    @PostMapping("/login")
    public void login(HttpServletRequest request,
        HttpServletResponse response){
       System.out.println("login");
    }
}
