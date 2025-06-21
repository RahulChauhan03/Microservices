package com.userService.user.service;

import org.springframework.stereotype.Service;

import com.userService.user.common.CommonResponse;
import com.userService.user.model.User;

@Service
public interface UserService {

	CommonResponse<Long> addUpdateUser(User user);

}
