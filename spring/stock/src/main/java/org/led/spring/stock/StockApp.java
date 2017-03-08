package org.led.spring.stock;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class StockApp 
{
    public static void main( String[] args )
    {
        SpringApplication sa = new SpringApplication(StockApp.class);
        sa.run(args);
    }
}
