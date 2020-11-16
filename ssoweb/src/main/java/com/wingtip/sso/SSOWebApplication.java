package com.wingtip.sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.wingtip.sso.security.ApplicationProperties;

@SpringBootApplication
public class SSOWebApplication {

	public static void main(String[] args) {
	//	try {
			SpringApplication.run(SSOWebApplication.class, args);
	//	}catch(Exception e) {
	//		if(e.getClass().getName().contains("SilentExitException")) {
	//            System.out.println("Spring is restarting the main thread - See spring-boot-devtools");
	//        } else {
	//            e.printStackTrace();
	//        }
	//	}
	}
	@Bean  // this can be autowired into Service
	public BCryptPasswordEncoder bCryptPasswordEncode(){
		return new BCryptPasswordEncoder();
	}
	@Bean(name="ApplicationProperties")
	public ApplicationProperties getAppProperties(){
		return new ApplicationProperties();
	}
	@Bean
	public WingtipSSOApplicationContext wingtipSSOApplicationContext() {
		return new WingtipSSOApplicationContext();
	}
}
