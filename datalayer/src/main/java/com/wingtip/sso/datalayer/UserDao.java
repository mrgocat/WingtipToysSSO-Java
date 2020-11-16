package com.wingtip.sso.datalayer;

import java.util.List;

import com.wingtip.sso.dto.UserDto;



public interface UserDao {
	List<UserDto> getList();
	UserDto find(String userId);
	boolean checkIdExists(String userId);
	
	String create(UserDto dto);
	void update(UserDto dto);
	void patch(String userId, String key, String value);
	void updatePassword(String userId, String passwordHash);
	void lockUser(String userId);
}
