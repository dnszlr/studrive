package com.zeller.studrive.orderservice.configuration;

import com.zeller.studrive.orderservice.basic.Constant;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.zeller.studrive.orderservice.repository")
public class MongoConfig extends AbstractMongoClientConfiguration {

	@Override
	@NotNull
	protected String getDatabaseName() {
		return Constant.DATABASE;
	}

	@Bean
	MongoTransactionManager transactionManager(MongoDatabaseFactory mongoDatabaseFactory) {
		return new MongoTransactionManager(mongoDatabaseFactory);
	}
}
