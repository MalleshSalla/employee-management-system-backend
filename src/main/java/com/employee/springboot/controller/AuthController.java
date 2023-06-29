package com.employee.springboot.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.employee.springboot.payload.JwtAuthResponse;
import com.employee.springboot.payload.LoginDto;
import com.employee.springboot.payload.RegisterDto;
import com.employee.springboot.payload.RoleDto;
import com.employee.springboot.service.AuthService;

@RestController
public class AuthController {
	@Autowired
	private AuthService authService;
	// private ResponseEntity<?> JwtAuthResponse;

	@PostMapping(value = { "/login" })
	public ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto) {

		return authService.login(loginDto);

	}

	@PostMapping(value = { "/register" })
	public ResponseEntity<Map<String, Object>> register(@RequestBody RegisterDto registerDto) {
		Map<String, Object> response = authService.register(registerDto);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PostMapping("/addRole")
	public ResponseEntity<RoleDto> addRole(@RequestBody RoleDto role) {

		return new ResponseEntity<RoleDto>(authService.createRole(role), HttpStatus.CREATED);

	}

}
