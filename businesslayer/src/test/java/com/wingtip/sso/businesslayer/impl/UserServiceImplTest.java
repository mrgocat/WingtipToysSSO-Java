package com.wingtip.sso.businesslayer.impl;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.wingtip.sso.datalayer.UserDao;
import com.wingtip.sso.datalayer.UserHistoryDao;
import com.wingtip.sso.datalayer.UserLoginLogsDao;
import com.wingtip.sso.datalayer.UserWrongPasswordDao;
import com.wingtip.sso.dto.UserDto;
import com.wingtip.sso.exception.ResourceNotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

	@Mock
	UserDao userDao;
	@Mock
	UserHistoryDao historyDao;
	@Mock
	UserLoginLogsDao loginLogsDao;
	@Mock
	UserWrongPasswordDao wrongPasswordDao;
	
//	@Spy
	@Mock
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@InjectMocks
	UserServiceImpl userService;
	
    @Before
    public void setUp(){
    
    }
	@Test
	public void createUserTest() {
		
		UserDto dto = new UserDto();
		dto.setEmail("abc@ggg.com");
		dto.setPassword("password");
		
		when(userDao.create(Mockito.any())).thenReturn(dto.getEmail());
		when(userDao.checkIdExists(Mockito.anyString())).thenReturn(false);
		
		when(bCryptPasswordEncoder.encode(dto.getPassword())).thenReturn("Hashed PWD");
		
		String userId = userService.createUser(dto);
		
		assertNotNull(dto);
		assertEquals(dto.getId(), userId);
		assertEquals("Hashed PWD", dto.getPasswordHash());
	}
	@Test
	public void patchUserTest() {
		UserDto dto = new UserDto();
		dto.setEmail("abc@ggg.com");
		dto.setPassword("password");
		dto.setFirstName("Ray");
		dto.setLastName("Kim");
		dto.setDateOfBirth(new Date());
		dto.setId("abc@ggg.com");
		when(userDao.find(dto.getId())).thenReturn(dto);

		userService.patchUser("abc@ggg.com", "email", "def@ggg.com");
		
		Mockito.verify(userDao).update(dto);
		
		userService.patchUser("abc@ggg.com", "dateOfBirth", "31/12/2010");
		
		Mockito.verify(userDao, Mockito.times(2)).update(dto);
		
		userService.patchUser("abc@ggg.com", "telephone", "416-2345-2222");
		
		Mockito.verify(userDao, Mockito.times(3)).update(dto);
	}
	
	@Test
	public void patchUserTest_ResourceNotFoundException() {
		when(userDao.find(Mockito.anyString())).thenReturn(null);
		
		assertThrows(ResourceNotFoundException.class, () -> userService.patchUser("abc@ggg.com", "email", "def@ggg.com"));
	}	
}
