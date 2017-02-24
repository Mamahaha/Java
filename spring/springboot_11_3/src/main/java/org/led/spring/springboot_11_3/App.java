package org.led.spring.springboot_11_3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
@RequestMapping(value="/users")
public class App 
{
	@RequestMapping("/haha")
	public String haha() {
		return "abcdaaaaa";
	}
	
	@RequestMapping(value="/{user}", method=RequestMethod.GET)
	public String getUserCustomer(@PathVariable String user, @RequestParam String customer) {
		return user + " has customer: " + customer;
	}
	
    public static void main( String[] args )
    {
        SpringApplication.run(App.class, args);
    }
}
