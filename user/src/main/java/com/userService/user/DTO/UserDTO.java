package com.userService.user.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
	
	 private Long userId;
	    private String userName;
	    private String firstName;
	    private String lastName;
	    private String email;

}
