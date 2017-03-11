package com.ericsson.embms.myconf.config;

import com.ericsson.embms.myconf.ApplicationProperties;

import lombok.Data;


@Data
@ApplicationProperties(locations = "classpath:bbcc.yml")
public class MyConfig {
    private String ccc;

    public String getCcc() {
        return ccc;
    }

    public void setCcc(String ccc) {
        this.ccc = ccc;
    }
}
