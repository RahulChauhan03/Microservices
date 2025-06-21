package com.userService.user.serviceImp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.userService.user.common.CommonResponse;
import com.userService.user.common.Constants;
import com.userService.user.common.HttpStatusCodes;
import com.userService.user.common.PasswordEncoderUtil;
import com.userService.user.model.User;
import com.userService.user.repo.userRepo;
import com.userService.user.service.UserService;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImp implements UserService {

	@Autowired
	userRepo userRepo;

	@Transactional
	@Override
	public CommonResponse<Long> addUpdateUser(User user) {
		CommonResponse<Long> response = new CommonResponse<>();

		try {
			User userToSave;
			if (user.getUserId() != null) {
				userToSave = userRepo.findById(user.getUserId())
						.orElseThrow(() -> new RuntimeException(Constants.USER_NOT_FOUND + user.getUserId()));

				userToSave.setEmail(user.getEmail());
				userToSave.setFirstName(user.getFirstName());
				userToSave.setLastName(user.getLastName());
				userToSave.setUserName(user.getUserName());
				if (!PasswordEncoderUtil.matches(user.getPassword(), userToSave.getPassword())) {
					userToSave.setPassword(PasswordEncoderUtil.encode(user.getPassword()));
				}
				response.setSuccessMessages(Constants.USER_UPDATED_SUCCESSFULLY);
			} else {
				user.setPassword(PasswordEncoderUtil.encode(user.getPassword()));
				userToSave = user;
				response.setSuccessMessages(Constants.USER_ADDED_SUCCSSFULLY);
			}
			User savedUser = userRepo.save(userToSave);
			response.setData(savedUser.getUserId());
			response.setStatus(HttpStatusCodes.OK);
			response.setSuccess(true);
		} catch (Exception e) {
			response.setData(null);
			response.setSuccessMessages(Constants.ERROR);
			response.setStatus(HttpStatusCodes.INTERNAL_SERVER_ERROR);
			response.setSuccess(false);
		}

		return response;
	}

}
