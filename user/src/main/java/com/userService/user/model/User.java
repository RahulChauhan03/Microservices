package com.userService.user.model;

import lombok.Data;

@Data
public class User {

	private Long userId;
	private String userName;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
		
}
