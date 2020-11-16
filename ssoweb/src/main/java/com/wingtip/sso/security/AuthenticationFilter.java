package com.wingtip.sso.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.dsig.keyinfo.KeyValue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.data.util.Pair;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.wingtip.sso.WingtipSSOApplicationContext;
import com.wingtip.sso.businesslayer.UserService;
import com.wingtip.sso.dto.UserDto;
import com.wingtip.sso.model.UserLoginRequest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter{
	private final AuthenticationManager authenticationManger;
	private final ApplicationContext context;

	private SecurityConfiguration secruityConfiguration;
	
	public AuthenticationFilter(AuthenticationManager authenticationManger, ApplicationContext context) {
		this.authenticationManger = authenticationManger;
		this.context = context;
		this.secruityConfiguration = context.getBean(SecurityConfiguration.class);
	}
	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, 
			HttpServletResponse res) throws AuthenticationException {
		UserLoginRequest creds = null;
		try {
				creds = new ObjectMapper()
						.readValue(req.getInputStream(),  UserLoginRequest.class);
				req.setAttribute("LoginIP", creds.getLoginIP());
				req.setAttribute("UserId", creds.getUserId());

				return authenticationManger.authenticate(
						new UsernamePasswordAuthenticationToken(
								creds.getUserId(), 
								creds.getPassword(),
								new ArrayList<>())
						);
		} catch(IOException ex) {
			throw new RuntimeException(ex);
		}
	}
	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, 
			FilterChain chain, Authentication auth) throws IOException{
		String userName = ((User)auth.getPrincipal()).getUsername();
			
		UserService userService = context.getBean(UserService.class);
		UserDto dto = userService.getUser(userName);
		Map<String, Object> claims = new HashMap<>();
		claims.put("nameidentifier", dto.getId());
		claims.put("name", dto.getFirstName() + " " + dto.getLastName());
		claims.put("emailaddress", dto.getEmail());
		
		String token = Jwts.builder()
				.setClaims(claims).setSubject(userName)
				.setExpiration(new Date(System.currentTimeMillis() + secruityConfiguration.getExpirationtime()))
				.setIssuer(secruityConfiguration.getIssuer())
				.setAudience(secruityConfiguration.getAudience())
				.signWith(SignatureAlgorithm.HS512, secruityConfiguration.getTokenSecret())
				.compact();
		
		res.addHeader(secruityConfiguration.HEADER_STRING, secruityConfiguration.TOKEN_PREFIX + token);
		res.addHeader("UserId",  dto.getId());
		
		JwtResponse resObj = new JwtResponse(token, dto.getFirstName() + " " + dto.getLastName());
		String jsonStr = new Gson().toJson(resObj);
		
		String loginIP = (String)req.getAttribute("LoginIP");

		userService.updateLoginResult(userName, loginIP, false, "Success.", false, 0);
		
        PrintWriter out = res.getWriter();
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        out.print(jsonStr);
        out.flush();  
		
	}
	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException failed)
			throws IOException, ServletException {
		SecurityContextHolder.clearContext();
				
		String failedReason = failed.getMessage(); //  User account is locked
		String failedMessage = failed.getMessage(); //  User account is locked
		UserService userService = context.getBean(UserService.class);
		
		String loginIP = (String)request.getAttribute("LoginIP");
		String userId = (String)request.getAttribute("UserId");
		boolean wrongPassword = false;
		// LockedException
		// DisabledException
		if(failed instanceof BadCredentialsException) {
			if(userService.checkIsExists(userId)) {
				wrongPassword = true;
				failedReason = "Wrong Password.";
			}else {
				failedReason = "User not exists. " + failedReason;
			}
		}
		boolean result = userService.updateLoginResult(userId, loginIP, true
				, failedReason, wrongPassword, secruityConfiguration.getMaxLoginAttempt());
		if(wrongPassword && result) { // 
			failedMessage = "Maximum password attempts exceeded. Account is locked";
		}
		// response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		response.setHeader("Reason", failedMessage);
		getFailureHandler().onAuthenticationFailure(request, response, failed);
		
	}	
}
class JwtResponse{
	private String token;
	private String username;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public JwtResponse(String token, String username) {
		super();
		this.token = token;
		this.username = username;
	}
	
}
