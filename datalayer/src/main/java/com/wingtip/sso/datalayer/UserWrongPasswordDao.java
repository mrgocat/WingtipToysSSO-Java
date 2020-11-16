package com.wingtip.sso.datalayer;

import com.wingtip.sso.dto.UserWrongPasswordDto;

public interface UserWrongPasswordDao {
	String create(UserWrongPasswordDto dto);
	UserWrongPasswordDto find(String userId);
	void increaseFailedCount(String userId);
	void delete(String userId);
}

