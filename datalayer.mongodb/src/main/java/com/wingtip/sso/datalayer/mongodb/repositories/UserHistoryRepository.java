package com.wingtip.sso.datalayer.mongodb.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.wingtip.sso.datalayer.mongodb.entities.UserHistoryEntity;

public interface UserHistoryRepository  extends MongoRepository<UserHistoryEntity, String>{

}
