package com.ericsson.embms.myconf.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ericsson.embms.myconf.ApplicationPropertiesBindingPostProcessor;
import com.ericsson.embms.myconf.config.MyConfig;

@RestController
@RequestMapping("/stock")
public class MyConfigController {

    @Bean
    public ApplicationPropertiesBindingPostProcessor applicationPropertiesBindingPostProcessor() {
        return new ApplicationPropertiesBindingPostProcessor();
    }
    @Autowired
    private MyConfig cfg;
    
    @RequestMapping(value="/loadstocks", method=RequestMethod.GET)
    public String loadStocks() {
        return cfg.getCcc();
    }
}
