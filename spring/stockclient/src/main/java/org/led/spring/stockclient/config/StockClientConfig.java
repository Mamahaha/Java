package org.led.spring.stockclient.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.squareup.okhttp.OkHttpClient;

@Configuration
public class StockClientConfig {
	@Bean
	public OkHttpClient okHttpClient() {
		return new OkHttpClient();
	}
}
