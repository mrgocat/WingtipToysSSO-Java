package com.wingtip.sso.datalayer.mongodb.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.wingtip.sso.datalayer.mongodb.entities.UserEntity;

public interface UserRepository extends MongoRepository<UserEntity, String>{

}
