package com.wingtip.sso.datalayer.mongodb;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;

import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

@Configuration
@EnableMongoRepositories("com.wingtip.sso.datalayer.mongodb.repositories")
public class MongoDBConfig extends AbstractMongoClientConfiguration {
	
	@Value("${spring.data.mongodb.uri}")
	public String mongoUri;
	@Value("${spring.data.mongodb.database}")
	public String database;

	@Override
	protected void configureClientSettings(MongoClientSettings.Builder builder) {
	    // customization hook
	    builder.applyConnectionString(new ConnectionString(mongoUri));
	}

	@Override
	protected String getDatabaseName() {
	    return database;
	}

}