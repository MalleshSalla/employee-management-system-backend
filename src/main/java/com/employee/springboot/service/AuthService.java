package com.employee.springboot.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.employee.springboot.payload.JwtAuthResponse;
import com.employee.springboot.payload.LoginDto;
import com.employee.springboot.payload.RegisterDto;
import com.employee.springboot.payload.RoleDto;

public interface AuthService {
	
	ResponseEntity<JwtAuthResponse> login(LoginDto loginDto);

	Map<String,Object>  register(RegisterDto registerDto);
	
	RoleDto createRole(RoleDto roleDto);

}
