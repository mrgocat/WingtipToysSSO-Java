package com.wingtip.sso.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ApplicationProperties {
	@Autowired
	private Environment env;
	public String getTokenSecret() {
		return env.getProperty("wingtipsso.tokenSecret");
	}
}
