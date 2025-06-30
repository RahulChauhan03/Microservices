package com.userService.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.userService.user.DTO.UserDTO;
import com.userService.user.common.CommonResponse;
import com.userService.user.model.User;
import com.userService.user.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserService userService;
	
	@PostMapping("/save/user") 
	public ResponseEntity<CommonResponse<Long>> addUpdateUser(@RequestBody User user) {
		return ResponseEntity.ok(userService.addUpdateUser(user));
	}
	
	@GetMapping("/get/user")
	public ResponseEntity<CommonResponse<UserDTO>> getUser(@RequestParam("userId") Long userId){
		return ResponseEntity.ok(userService.getUser(userId));
	}
  
	@GetMapping("/delete/user")
	public ResponseEntity<CommonResponse<Integer>> deleteUser(@RequestParam("userId") Long userId){
		return ResponseEntity.ok(userService.deleteUser(userId));
	}
	
	@PostMapping("/buy/product")
    public ResponseEntity<CommonResponse<Integer>> buyProduct(@RequestParam("userId") Long userId,
                                             @RequestParam("productId") Long productId) {
		return ResponseEntity.ok(userService.buyProduct(userId,productId));
	}
}
