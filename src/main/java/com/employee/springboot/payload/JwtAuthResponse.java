package com.employee.springboot.payload;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthResponse 
{
	private String name;
	private String accessToken;
	private String tokenType="Bearer";
	private String roleName;
}
