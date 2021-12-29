package com.zeller.studrive.studrivegateway;

import com.zeller.studrive.openapi.OpenAPIConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Configuration
@Import({OpenAPIConfiguration.class})
public class StudriveGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudriveGatewayApplication.class, args);
	}
}
