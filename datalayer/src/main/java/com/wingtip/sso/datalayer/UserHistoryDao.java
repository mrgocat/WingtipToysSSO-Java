package com.wingtip.sso.datalayer;

public interface UserHistoryDao {
	String create(String userId, String updateUserId, String updateReason);
}
