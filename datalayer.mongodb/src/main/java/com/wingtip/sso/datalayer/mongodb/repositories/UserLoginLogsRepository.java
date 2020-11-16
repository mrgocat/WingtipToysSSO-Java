package com.wingtip.sso.datalayer.mongodb.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.wingtip.sso.datalayer.mongodb.entities.UserLoginLogsEntity;

public interface UserLoginLogsRepository  extends MongoRepository<UserLoginLogsEntity, String>{

}
