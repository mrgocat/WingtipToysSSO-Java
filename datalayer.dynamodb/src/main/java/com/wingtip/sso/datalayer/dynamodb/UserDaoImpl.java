package com.wingtip.sso.datalayer.dynamodb;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import com.wingtip.sso.datalayer.UserDao;
import com.wingtip.sso.datalayer.dynamodb.entities.UserEntity;
import com.wingtip.sso.dto.UserDto;
import com.wingtip.sso.exception.ResourceNotFoundException;

@Repository
public class UserDaoImpl implements UserDao{

	private DynamoDBUserRepository repository;

	public UserDaoImpl(DynamoDBUserRepository repository){
		this.repository = repository;
	}
	
	@Override
	public List<UserDto> getList() {
		Iterable<UserEntity> iterable = repository.findAll();
		List<UserDto> list = new ArrayList<>();
		iterable.forEach(entity -> {
			UserDto dto = new UserDto();
			BeanUtils.copyProperties(entity, dto);
			list.add(dto);
		});
		
		return list;
	}

	@Override
	public UserDto find(String userId) {
		Optional<UserEntity> optional = repository.findById(userId);
		if(!optional.isPresent()) {
			throw new ResourceNotFoundException("No data for id-" + userId);
		}

		UserDto dto = new UserDto();
		BeanUtils.copyProperties(optional.get(), dto);
		return dto;
	}

	@Override
	public boolean checkIdExists(String userId) {
		Optional<UserEntity> optional = repository.findById(userId);
		
		return optional.isPresent();
	}

	@Override
	public String create(UserDto dto) {
		UserEntity entity = new UserEntity();
		BeanUtils.copyProperties(dto, entity);
		repository.save(entity);
		return entity.getId();
	}

	@Override
	public void update(UserDto dto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void patch(String userId, String key, String value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updatePassword(String userId, String passwordHash) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void lockUser(String userId) {
		// TODO Auto-generated method stub
		
	}

}
