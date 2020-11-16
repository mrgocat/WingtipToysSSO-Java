package com.wingtip.sso.datalayer.dynamodb;

import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.wingtip.sso.dto.UserDto;
import com.wingtip.sso.datalayer.dynamodb.entities.UserEntity;
import com.wingtip.sso.exception.ResourceNotFoundException;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class UserRepositoryImplTest {
	@InjectMocks
	private UserDaoImpl daoImpl;
	@Mock
	private DynamoDBUserRepository userRepository;

	@Test
	public void getUserTest() {
		UserEntity entity = new UserEntity(); 
		entity.setId("ggg@gmail.com");
		entity.setFirstName("Ray");
		entity.setFirstName("Kim");
		
		when(userRepository.findById(entity.getId())).thenReturn(Optional.of(entity));
		
		UserDto dto = daoImpl.find(entity.getId());
		assertNotNull(dto);
		assertEquals(entity.getId(), dto.getId());
	}
}
