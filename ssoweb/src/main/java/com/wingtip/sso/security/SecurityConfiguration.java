package com.wingtip.sso.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfiguration {
	public static final long EXPIRATION_TIME = 86400000; // 10 days
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String SIGN_UP_URL = "/api/v1/users";
	
	@Value("${wingtipsso.tokenSecret}")
	private String tokenSecret;
	
	@Value("${jwt.expirationtime}")
	private long expirationtime;
	
	@Value("${jwt.issuer}")
	private String issuer;
	
	@Value("${jwt.audience}")
	private String audience;
	
	@Value("${wingtipsso.MaxLoginAttempt}")
	private int maxLoginAttempt = 5;
	
	public String getIssuer() {
		return issuer;
	}
	public String getAudience() {
		return audience;
	}
	public String getTokenSecret() {
		return tokenSecret;
	}
	public long getExpirationtime() {
		return expirationtime;
	}
	public int getMaxLoginAttempt() {
		return maxLoginAttempt;
	}
}
