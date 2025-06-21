package com.userService.user.serviceImp;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.userService.user.common.CommonResponse;
import com.userService.user.common.Constants;
import com.userService.user.common.HttpStatusCodes;
import com.userService.user.common.PasswordEncoderUtil;
import com.userService.user.model.User;
import com.userService.user.repo.userRepo;
import com.userService.user.service.UserService;

import jakarta.transaction.Transactional;

public class UserServiceImp implements UserService {

	@Autowired
	userRepo userRepo;

	@Transactional
	@Override
	public CommonResponse<Integer> addUpdateUser(User user) {
		CommonResponse<Integer> response = new CommonResponse<>();

		try {

			if (user.getUserId() != null) {
				Optional<User> existingUser = userRepo.findById(user.getUserId());
				User existingUserDetail = existingUser.get();
				existingUserDetail.setEmail(user.getEmail());
				existingUserDetail.setFirstName(user.getFirstName());
				existingUserDetail.setLastName(user.getLastName());
				existingUserDetail.setUserName(user.getUserName());
				if (!PasswordEncoderUtil.matches(user.getPassword(), existingUserDetail.getPassword())) {
					existingUserDetail.setPassword(PasswordEncoderUtil.encode(user.getPassword()));
				}
				userRepo.save(existingUserDetail);
				response.setError(Constants.USER_UPDATED_SUCCSSFULLY);
			} else {
				user.setPassword(PasswordEncoderUtil.encode(user.getPassword()));
				userRepo.save(user);
				response.setError(Constants.USER_ADDED_SUCCSSFULLY);
			}
			response.setStatus(HttpStatusCodes.OK);
			response.setSuccess(true);
		} catch (Exception e) {
			response.setData(null);
			response.setError(Constants.ERROR);
			response.setStatus(HttpStatusCodes.INTERNAL_SERVER_ERROR);
			response.setSuccess(false);
		}

		return response;
	}

}
