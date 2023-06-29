package com.employee.springboot.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.employee.springboot.entity.Role;
import com.employee.springboot.entity.User;
import com.employee.springboot.exception.EmployeeApiException;
import com.employee.springboot.payload.JwtAuthResponse;
import com.employee.springboot.payload.LoginDto;
import com.employee.springboot.payload.RegisterDto;
import com.employee.springboot.payload.RoleDto;
import com.employee.springboot.repository.RoleRepository;
import com.employee.springboot.repository.UserRepository;
import com.employee.springboot.security.JwtTokenProvider;
import com.employee.springboot.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	private Authentication authentication;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	

	public ResponseEntity<JwtAuthResponse> login(LoginDto loginDto) {

		try {
			authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginDto.getUserNameOrEmail(), loginDto.getPassword()));
		} catch (Exception e) {
			return ResponseEntity.ok(new JwtAuthResponse("No","No", "No", "No"));
		}

		List<User> users = userRepository.findAll();

		String roleName = null;

		for (User user : users) {

			if ((loginDto.getUserNameOrEmail()).equals(user.getUserName())
					|| (loginDto.getUserNameOrEmail()).equals(user.getEmail())) {

				if (user.getUserId() == 1) {
					roleName = "Admin";
				} else {
					roleName = "User";
				}
			}
		}

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtTokenProvider.generateToken(authentication);

		return ResponseEntity.ok(new JwtAuthResponse(loginDto.getUserNameOrEmail(),token, "Bearer", roleName));

	}

	public Map<String,Object>  register(RegisterDto registerDto) {

		// Check weather user exist with same user name and email
		if (userRepository.existsByUserName(registerDto.getUserName())) {
			throw new EmployeeApiException(HttpStatus.BAD_REQUEST, "Username is already exists");
		}

		// Check weather user exist with same email
		if (userRepository.existsByEmail(registerDto.getEmail())) {
			throw new EmployeeApiException(HttpStatus.BAD_REQUEST, "Email is already exists");
		}

		User user = new User();
		user.setName(registerDto.getName());
		user.setUserName(registerDto.getUserName());
		user.setEmail(registerDto.getEmail());
		user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
		

		Set<Role> roles = new HashSet<>();
		// By default new Registered user is assigned with role "ROLE_USER"
		Role userRole = roleRepository.findByName("ROLE_USER").get();
		roles.add(userRole);
		user.setRoles(roles);
		user.setUserId(userRole.getId());

		userRepository.save(user);
		
		
		Map<String,Object> obj = new HashMap<String,Object>();
		
		obj.put("Id",user.getId());
		obj.put("User Name", user.getUserName());
		obj.put("User Email", user.getEmail());

		return obj;

		
	}
	

	@Override
	public RoleDto createRole(RoleDto roleDto) {
		List<Role> roles = roleRepository.findAll();
		
		for(Role role: roles) {
			
			if((role.getName()).equals(roleDto.getRole())) {
				 throw new EmployeeApiException(HttpStatus.BAD_REQUEST, "Username is already exists");
			}
		}
	
		Role role = new Role();		
		role.setName(roleDto.getRole());
		
		Role savedRole = roleRepository.save(role);
		
		roleDto.setId(savedRole.getId());
		roleDto.setRole(savedRole.getName());
		
		return roleDto;	
	}
	

}
