package org.led.spring.stock.config;


import org.led.spring.stock.common.ApplicationProperties;

import lombok.Data;


@Data
@ApplicationProperties(locations = "classpath:cof2.properties")
public class StockConfig {

    private String max1;

    public String getMax1() {
        return max1;
    }

    public void setMax1(String max1) {
        this.max1 = max1;
    }

}
