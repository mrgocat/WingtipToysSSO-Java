package com.wingtip.sso.datalayer.mongodb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import com.wingtip.sso.datalayer.UserDao;
import com.wingtip.sso.datalayer.mongodb.entities.UserEntity;
import com.wingtip.sso.datalayer.mongodb.repositories.UserRepository;
import com.wingtip.sso.dto.UserDto;
import com.wingtip.sso.exception.ResourceNotFoundException;

@Repository
public class UserDaoImpl implements UserDao{
	private UserRepository repository;

	public UserDaoImpl(UserRepository repository){
		this.repository = repository;
	}

	@Override
	public List<UserDto> getList() {
		List<UserEntity> entityList = repository.findAll();
		List<UserDto> list = entityList.stream().map(item -> {
			UserDto dto = new UserDto();
			BeanUtils.copyProperties(item, dto);
			return dto;
		}).collect(Collectors.toList());
		
		return list;
	}

	@Override
	public UserDto find(String userId) {
		Optional<UserEntity> optional = repository.findById(userId);
		if(!optional.isPresent()) {
			return null; // throw new ResourceNotFoundException("No data for id-" + userId);
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
		Optional<UserEntity> optional = repository.findById(dto.getId());
		if(!optional.isPresent()) {
			throw new ResourceNotFoundException("No data for id-" + dto.getId());
		}		
		UserEntity entity = optional.get();
		entity.setEmail(dto.getEmail());
		entity.setFirstName(dto.getFirstName());
		entity.setLastName(dto.getLastName());
		entity.setTelephone(dto.getTelephone());
		entity.setNationality(dto.getNationality());
		entity.setDateOfBirth(dto.getDateOfBirth());
		
		repository.save(entity);
	}

	@Override
	public void patch(String userId, String key, String value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updatePassword(String userId, String passwordHash) {
		Optional<UserEntity> optional = repository.findById(userId);
		if(!optional.isPresent()) {
			throw new ResourceNotFoundException("No data for id-" + userId);
		}		
		UserEntity entity = optional.get();
		entity.setPasswordHash(passwordHash);
		entity.setLastUpdated(new Date());
				
		repository.save(entity);
	}

	@Override
	public void lockUser(String userId) {
		Optional<UserEntity> optional = repository.findById(userId);
		if(!optional.isPresent()) {
			throw new ResourceNotFoundException("No data for id-" + userId);
		}		
		UserEntity entity = optional.get();
		entity.setLocked(true);
		entity.setLastUpdated(new Date());
				
		repository.save(entity);
	}
	
}
