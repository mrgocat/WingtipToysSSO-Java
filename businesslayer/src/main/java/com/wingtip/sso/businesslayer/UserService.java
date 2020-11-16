package com.wingtip.sso.businesslayer;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.wingtip.sso.dto.UserDto;



public interface UserService extends UserDetailsService{
	public static final String[] AcceptedKeys 
		= new String[]{ "email", "firstName", "lastName", "telephone", "nationality", "dateOfBirth" };
	
	UserDto getUser(String userId);
	List<UserDto> getUsers();
	
	boolean checkIsExists(String userId);
	boolean updateLoginResult(String userId, String loginIP, boolean failed, String faildedReason
			, boolean wrongPassword, int maxLoginAttempt);
	
	String createUser(UserDto dto);
	void updatePassword(String userId, String oldPassword, String newPassword);
	void updateUser(UserDto dto);
	void patchUser(String userId, String key, String value);
}
