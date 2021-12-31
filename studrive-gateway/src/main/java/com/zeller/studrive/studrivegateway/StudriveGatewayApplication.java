package com.zeller.studrive.studrivegateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.SwaggerUiConfigParameters;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@Configuration
public class StudriveGatewayApplication {

	private final Logger logger = LoggerFactory.getLogger(StudriveGatewayApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(StudriveGatewayApplication.class, args);
	}

	@Bean
	public List<GroupedOpenApi> apis(SwaggerUiConfigParameters swaggerUiConfigParameters, RouteDefinitionLocator locator) {
		List<GroupedOpenApi> groups = new ArrayList<>();
		List<RouteDefinition> definitions = locator.getRouteDefinitions().collectList().block();
		for(RouteDefinition definition : definitions) {
			logger.info("id: " + definition.getId() + "  " + definition.getUri().toString());
		}
		definitions.stream().filter(routeDefinition -> routeDefinition.getId().matches(".*-service")).forEach(routeDefinition -> {
			String name = routeDefinition.getId().replaceAll("-service", "");
			swaggerUiConfigParameters.addGroup(name);
			GroupedOpenApi.builder().pathsToMatch("/" + name + "/**").group(name).build();
			logger.info("Group: " + "/" + name + "/**");
		});
		return groups;
	}
}
