package org.led.spring.java_bean_conf.conf;

import org.led.spring.java_bean_conf.beans.Plane;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

	@Bean(name = "airplane")
	public Plane plane() {
		Plane p = new Plane();
		p.setBand("USA");
		p.setPrice(10000);
		return p;
	}
}
