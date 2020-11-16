package com.wingtip.sso.datalayer.mongodb;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import com.wingtip.sso.datalayer.UserWrongPasswordDao;
import com.wingtip.sso.datalayer.mongodb.entities.UserEntity;
import com.wingtip.sso.datalayer.mongodb.entities.UserWrongPasswordEntity;
import com.wingtip.sso.datalayer.mongodb.repositories.UserWrongPasswordRepository;
import com.wingtip.sso.dto.UserDto;
import com.wingtip.sso.dto.UserWrongPasswordDto;
import com.wingtip.sso.exception.ResourceNotFoundException;

@Repository
public class UserWrongPasswordDaoImpl implements UserWrongPasswordDao {
	private UserWrongPasswordRepository repository;

	public UserWrongPasswordDaoImpl(UserWrongPasswordRepository repository){
		this.repository = repository;
	}
	@Override
	public String create(UserWrongPasswordDto dto) {
		UserWrongPasswordEntity entity = new UserWrongPasswordEntity();

		BeanUtils.copyProperties(dto, entity);
		
		entity = repository.save(entity);
		return entity.getId();
	}

	@Override
	public UserWrongPasswordDto find(String userId) {
		Optional<UserWrongPasswordEntity> optional = repository.findById(userId);
		if(!optional.isPresent()) {
			return null;
		}

		UserWrongPasswordDto dto = new UserWrongPasswordDto();
		BeanUtils.copyProperties(optional.get(), dto);
		return dto;
	}

	@Override
	public void increaseFailedCount(String userId) {
		Optional<UserWrongPasswordEntity> optional = repository.findById(userId);
		if(optional.isPresent()) {
			UserWrongPasswordEntity entity = optional.get();
			entity.setFialedCount(entity.getFialedCount()+1);
			repository.save(entity);
		}
		UserWrongPasswordEntity entity = new UserWrongPasswordEntity();
		entity.setId(userId);
		entity.setFialedCount(1);
		repository.save(entity);
	}

	@Override
	public void delete(String userId) {
		repository.deleteById(userId);
	}

}
