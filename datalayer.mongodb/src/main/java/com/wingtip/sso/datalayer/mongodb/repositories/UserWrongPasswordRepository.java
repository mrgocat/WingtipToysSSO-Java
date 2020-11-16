package com.wingtip.sso.datalayer.mongodb.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.wingtip.sso.datalayer.mongodb.entities.UserWrongPasswordEntity;

public interface UserWrongPasswordRepository extends MongoRepository<UserWrongPasswordEntity, String>{

}
