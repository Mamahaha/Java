package org.led.spring.springboot_11_3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class App 
{
	@RequestMapping("/")
	public String haha() {
		return "abcdaaaaa";
	}
    public static void main( String[] args )
    {
        SpringApplication.run(App.class, args);
    }
}
