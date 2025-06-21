package com.userService.user.common;

import lombok.Data;

@Data
public class CommonResponse<T> {
	
	T data;
	String error;
	Integer status;
	boolean success;
}
