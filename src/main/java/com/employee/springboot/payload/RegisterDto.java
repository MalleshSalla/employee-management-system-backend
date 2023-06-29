package com.employee.springboot.payload;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto 
{
	private String name;
	private String userName;
	private String email;
	private String password;
	private long userRole;
	
}