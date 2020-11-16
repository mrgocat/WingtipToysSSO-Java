package com.wingtip.sso.datalayer.dynamodb;

import java.util.List;
import java.util.Optional;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import com.wingtip.sso.datalayer.dynamodb.entities.UserEntity;


@EnableScan
public interface DynamoDBUserRepository extends CrudRepository<UserEntity, String>{
	Optional<UserEntity> findById(String userId);
	List<UserEntity> findAll();
}
