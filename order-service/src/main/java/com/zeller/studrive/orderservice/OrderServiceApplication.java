package com.zeller.studrive.orderservice;

import com.zeller.studrive.httptrace.HttpTraceConfiguration;
import com.zeller.studrive.openapi.OpenAPIConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Configuration
@Import({OpenAPIConfiguration.class, HttpTraceConfiguration.class})
public class OrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}
}
