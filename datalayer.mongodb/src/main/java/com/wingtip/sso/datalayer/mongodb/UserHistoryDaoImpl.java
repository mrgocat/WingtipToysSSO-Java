package com.wingtip.sso.datalayer.mongodb;

import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.wingtip.sso.datalayer.UserHistoryDao;
import com.wingtip.sso.datalayer.mongodb.entities.UserEntity;
import com.wingtip.sso.datalayer.mongodb.entities.UserHistoryEntity;
import com.wingtip.sso.datalayer.mongodb.repositories.UserHistoryRepository;
import com.wingtip.sso.datalayer.mongodb.repositories.UserRepository;

@Repository
public class UserHistoryDaoImpl implements UserHistoryDao {
	private UserHistoryRepository repository;
	private UserRepository userRepository;

	public UserHistoryDaoImpl(UserHistoryRepository repository, UserRepository userRepository){
		this.repository = repository;
		this.userRepository = userRepository;
	}
	@Override
	public String create(String userId, String updateUserId, String updateReason) {

		Optional<UserEntity> optional = userRepository.findById(userId);
		if(optional.isPresent()) {
			UserEntity user = optional.get();
			
			UserHistoryEntity entity = new UserHistoryEntity();
			entity.setUserId(userId);
			entity.setUpdated(new Date());
			entity.setUpdateUserId(updateUserId);
			entity.setUpdateReason(updateReason);
			entity.setUser(user);
			
			entity = repository.save(entity);
			return entity.getId();
		}else {
			return null;
		}
	}

}
