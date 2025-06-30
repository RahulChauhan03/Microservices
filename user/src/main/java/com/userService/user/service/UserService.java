package com.userService.user.service;

import org.springframework.stereotype.Service;

import com.userService.user.DTO.UserDTO;
import com.userService.user.common.CommonResponse;
import com.userService.user.model.User;

@Service
public interface UserService {

	CommonResponse<Long> addUpdateUser(User user);

	CommonResponse<UserDTO> getUser(Long userId);

	CommonResponse<Integer> deleteUser(Long userId);

	CommonResponse<Integer> buyProduct(Long userId, Long productId);
}
