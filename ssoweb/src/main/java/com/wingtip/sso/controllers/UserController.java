package com.wingtip.sso.controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.wingtip.sso.businesslayer.UserService;
import com.wingtip.sso.dto.UserDto;
import com.wingtip.sso.exception.AuthException;
import com.wingtip.sso.model.UserCreateRequest;
import com.wingtip.sso.model.UserKeyValueRequest;
import com.wingtip.sso.model.UserPasswordChangeRequest;
import com.wingtip.sso.model.UserResponse;
import com.wingtip.sso.model.UserUpdateRequest;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
	private UserService service;
	public UserController(UserService service) {
		this.service = service;
	}
	@GetMapping("{userId}")
	public UserResponse getUser(@PathVariable String userId) {
		UserDto dto = service.getUser(userId);
		UserResponse response = new UserResponse();
		BeanUtils.copyProperties(dto, response);
		return response;
	}
	@GetMapping
	public List<UserResponse> getUsers(){
		List<UserDto> dtoList = service.getUsers();
		List<UserResponse> responseList = dtoList.stream().map(item -> {
			UserResponse response = new UserResponse();
			BeanUtils.copyProperties(item, response);
			return response;
		}).collect(Collectors.toList());
		return responseList;
	}
	@PostMapping
	@ResponseStatus( HttpStatus.CREATED )
	public String createUser(@Valid @RequestBody UserCreateRequest crateRequest) {
		UserDto dto = new UserDto();
		BeanUtils.copyProperties(crateRequest, dto);
		String userId = service.createUser(dto);
		return userId;
	}
	@GetMapping("{userId}/CheckIdExists")
	public CheckIdExistsResponse CheckIdExists(@PathVariable String userId) {
		boolean result = service.checkIsExists(userId);
		return new CheckIdExistsResponse(result);
	}
	private String getUserId() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if(auth == null) {
			throw new AuthException("No auth info. null");
		}
		UsernamePasswordAuthenticationToken userInfo = (UsernamePasswordAuthenticationToken)auth;
		if(userInfo.getPrincipal() == null) {
			throw new AuthException("No auth info. no principal");
		}
		return (String)userInfo.getPrincipal();
	}
	@PutMapping
	public ReturnOk updateUser(@Valid @RequestBody UserUpdateRequest request) {
		String userId = getUserId();
		
		UserDto dto = service.getUser(userId);
		
		BeanUtils.copyProperties(request, dto);
		
		service.updateUser(dto);
		
		return new ReturnOk("ok");
	}
	@PatchMapping
	public ReturnOk patchUser(@Valid @RequestBody UserPasswordChangeRequest req) {
		String userId = getUserId();
		
		service.updatePassword(userId, req.getOldPassword(), req.getNewPassword());

		return new ReturnOk("ok");
	}
	@PatchMapping("{userId}")
	public ReturnOk patchUser(@PathVariable String userId, @RequestBody UserKeyValueRequest req) {
		String id = getUserId();
		if(!id.equals(userId)) {
			throw new AuthException("Not Autholized");
		}
		service.patchUser(userId, req.getKey(), req.getValue());

		return new ReturnOk("ok");
	}

}
class ReturnOk{
	private String result;
	public ReturnOk(String result) {
		super();
		this.result = result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getResult() {
		return result;
	}
}
class CheckIdExistsResponse{
	private boolean exists;

	public CheckIdExistsResponse(boolean exists) {
		super();
		this.exists = exists;
	}

	public boolean getExists() {
		return exists;
	}

	public void setExists(boolean exists) {
		this.exists = exists;
	}
	
}
