package com.wingtip.sso.businesslayer.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.yaml.snakeyaml.introspector.PropertyUtils;

import com.wingtip.sso.businesslayer.UserService;
import com.wingtip.sso.datalayer.UserDao;
import com.wingtip.sso.datalayer.UserHistoryDao;
import com.wingtip.sso.datalayer.UserLoginLogsDao;
import com.wingtip.sso.datalayer.UserWrongPasswordDao;
import com.wingtip.sso.dto.UserDto;
import com.wingtip.sso.dto.UserLoginLogsDto;
import com.wingtip.sso.dto.UserWrongPasswordDto;
import com.wingtip.sso.exception.BadRequestException;
import com.wingtip.sso.exception.ResourceNotFoundException;

@Service
public class UserServiceImpl implements UserService{
	private UserDao userDao;
	
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	private UserHistoryDao historyDao;

	private UserLoginLogsDao loginLogsDao;

	private UserWrongPasswordDao wrongPasswordDao;
	
	public UserServiceImpl(UserDao userDao
			, UserHistoryDao historyDao
			, UserLoginLogsDao loginLogsDao
			, UserWrongPasswordDao wrongPasswordDao
			, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userDao = userDao;
		this.historyDao = historyDao;
		this.loginLogsDao = loginLogsDao;
		this.wrongPasswordDao = wrongPasswordDao;
		
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	@Override
	public UserDto getUser(String userId) {
		return userDao.find(userId);
	}

	@Override
	public List<UserDto> getUsers() {
		return userDao.getList();
	}

	@Override
	public boolean checkIsExists(String userId) {
		return userDao.checkIdExists(userId);
	}

	@Override
	public String createUser(UserDto dto) {
		dto.setId(dto.getEmail());
		
		if(userDao.checkIdExists(dto.getId())) {
			throw new BadRequestException(dto.getId() + " has already been taken.");
		}
		dto.setActivated(true);
		dto.setLocked(false);
		dto.setCreated(new Date());
		dto.setLastUpdated(dto.getCreated());
		dto.setAdmin(false);
		dto.setUser(true);
		
		// password hash... 
		String encodedPassword = bCryptPasswordEncoder.encode(dto.getPassword());
		dto.setPasswordHash(encodedPassword);
		
		dto.setPassword(null);
		String userId = userDao.create(dto);
		historyDao.create(userId, userId, "Created new.");
		return userId;
	}

	@Override
	public void updatePassword(String userId, String oldPassword, String newPassword) {
		UserDto dto = userDao.find(userId);
		if(dto == null) {
			throw new ResourceNotFoundException("Cannot find user. Id-" + userId);
		}
		
		String encodedPassword = bCryptPasswordEncoder.encode(oldPassword);
		if(!encodedPassword.equals(dto.getPasswordHash())) {
			throw new BadRequestException("Password is not maptch.");
		}
		encodedPassword = bCryptPasswordEncoder.encode(newPassword);
		userDao.updatePassword(userId, encodedPassword);
		historyDao.create(userId, userId, "Password update.");
	}

	@Override
	public void updateUser(UserDto dto) {
		userDao.update(dto);
		historyDao.create(dto.getId(), dto.getId(), "Update user");
	}

	@Override
	public void patchUser(String userId, String key, String value) {
		if(!Arrays.stream(AcceptedKeys).anyMatch(s -> s.equals(key))) {
			throw new BadRequestException("You cannot change column" + key);
		}
		UserDto dto = userDao.find(userId);
		if(dto == null) {
			throw new ResourceNotFoundException("Cannot find user. Id-" + userId);
		}
		
		BeanWrapper wrapper = new BeanWrapperImpl(dto);
		wrapper.setPropertyValue(key, value);
		dto = (UserDto)wrapper.getWrappedInstance();
		
		userDao.update(dto);
	}

	@Override // from UserDetailsService
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDto dto = userDao.find(username);
		if(dto == null) throw new UsernameNotFoundException("No data for id-" + username);

	//	return new User(dto.getId(), dto.getPasswordHash(), new ArrayList<>());
		return new User(dto.getId(), dto.getPasswordHash()
				, dto.isActivated(), true, true, !dto.isLocked(), new ArrayList<>());
	}
	private void createLoginLog(String userId, String loginIP, boolean failed, String failedReason) {
		UserLoginLogsDto dto = new UserLoginLogsDto();
		dto.setUserId(userId);
		dto.setLoginIP(loginIP);
		dto.setFailed(failed);
		dto.setFailedReason(failedReason);
		loginLogsDao.create(dto);
	}
	@Override
	public boolean updateLoginResult(String userId, String loginIP, boolean failed, String faildedReason
			, boolean wrongPassword, int maxLoginAttempt) {
		boolean isLocked = false;
		if(wrongPassword) {
			UserWrongPasswordDto dto = wrongPasswordDao.find(userId);
			if(dto != null && dto.getFialedCount() >= maxLoginAttempt) {
				wrongPasswordDao.delete(userId);
				userDao.lockUser(userId);
				isLocked = true;
			} else {
				wrongPasswordDao.increaseFailedCount(userId);
			}
		} else {
			createLoginLog(userId, loginIP, failed, faildedReason);
			if(!failed) {
				wrongPasswordDao.delete(userId);
			}
		}
		return isLocked;
	}
	
}
