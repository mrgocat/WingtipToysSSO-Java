package com.wingtip.sso.datalayer.mongodb;

import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import com.wingtip.sso.datalayer.UserLoginLogsDao;
import com.wingtip.sso.datalayer.mongodb.entities.UserLoginLogsEntity;
import com.wingtip.sso.datalayer.mongodb.repositories.UserLoginLogsRepository;
import com.wingtip.sso.dto.UserLoginLogsDto;

@Repository
public class UserLoginLogsDaoImpl implements UserLoginLogsDao {
	private UserLoginLogsRepository repository;

	public UserLoginLogsDaoImpl(UserLoginLogsRepository repository){
		this.repository = repository;
	}
	@Override
	public String create(UserLoginLogsDto dto) {
		UserLoginLogsEntity entity = new UserLoginLogsEntity();

		BeanUtils.copyProperties(dto, entity);
		
		entity = repository.save(entity);
		return entity.getId();
	}

}
