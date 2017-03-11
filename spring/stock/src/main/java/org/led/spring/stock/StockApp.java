package org.led.spring.stock;


import org.led.spring.stock.common.ApplicationPropertiesBindingPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class StockApp 
{
    @Bean
    public ApplicationPropertiesBindingPostProcessor applicationPropertiesBindingPostProcessor() {
        return new ApplicationPropertiesBindingPostProcessor();
    }
    
    public static void main( String[] args )
    {
        SpringApplication sa = new SpringApplication(StockApp.class);
        sa.run(args);
    }
}
