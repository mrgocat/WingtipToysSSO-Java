package com.wingtip.sso.datalayer;

import com.wingtip.sso.dto.UserLoginLogsDto;

public interface UserLoginLogsDao {
	String create(UserLoginLogsDto dto);
}
