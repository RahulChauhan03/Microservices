package com.userService.user.serviceImp;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.productService.product.DTO.ProductDTO;
import com.userService.user.DTO.UserDTO;
import com.userService.user.common.CommonResponse;
import com.userService.user.common.Constants;
import com.userService.user.common.HttpStatusCodes;
import com.userService.user.common.PasswordEncoderUtil;
import com.userService.user.config.ProductClient;
import com.userService.user.model.Purchase;
import com.userService.user.model.User;
import com.userService.user.repo.PurchaseRepo;
import com.userService.user.repo.userRepo;
import com.userService.user.service.UserService;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImp implements UserService {

	@Autowired
	userRepo userRepo;

	@Autowired
	private ProductClient productClient;

	@Autowired
	PurchaseRepo purchaseRepo;

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
			e.printStackTrace();
			response.setData(null);
			response.setSuccessMessages(Constants.ERROR);
			response.setStatus(HttpStatusCodes.INTERNAL_SERVER_ERROR);
			response.setSuccess(false);
		}

		return response;
	}

	@Override
	public CommonResponse<UserDTO> getUser(Long userId) {
		CommonResponse<UserDTO> response = new CommonResponse<>();

		try {
			Optional<User> user = userRepo.findById(userId);
			if (user.isPresent()) {
				UserDTO userDto = new UserDTO(user.get().getUserId(), user.get().getFirstName(),
						user.get().getUserName(), user.get().getLastName(), user.get().getEmail());
				response.setData(userDto);
				response.setSuccessMessages(Constants.GET_USER_SUCCSSFULLY);
			} else {
				response.setData(null);
				response.setSuccessMessages(Constants.USER_NOT_FOUND + userId);
			}
			response.setStatus(HttpStatusCodes.OK);
			response.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			response.setData(null);
			response.setSuccessMessages(Constants.ERROR);
			response.setStatus(HttpStatusCodes.INTERNAL_SERVER_ERROR);
			response.setSuccess(false);
		}
		return response;
	}

	@Override
	public CommonResponse<Integer> deleteUser(Long userId) {
		CommonResponse<Integer> response = new CommonResponse<>();
		try {

			if (userRepo.existsById(userId)) {
				userRepo.deleteById(userId);
				response.setData(1);
				response.setSuccessMessages(Constants.USER_DELETED_SUCCSSFULLY);
			} else {
				response.setData(null);
				response.setSuccessMessages(Constants.USER_NOT_FOUND + userId);
			}
			response.setStatus(HttpStatusCodes.OK);
			response.setSuccess(false);
		} catch (Exception e) {
			e.printStackTrace();
			response.setData(null);
			response.setSuccessMessages(Constants.ERROR);
			response.setStatus(HttpStatusCodes.INTERNAL_SERVER_ERROR);
			response.setSuccess(false);
		}
		return response;
	}

	@Override
	public CommonResponse<Integer> purchaseProduct(Long userId, Long productId) {
		CommonResponse<Integer> response = new CommonResponse<>();
		try {
			CommonResponse<ProductDTO> productResponse = productClient.getProductById(productId);
			if (productResponse.getData() != null) {
				Purchase purchaseProduct = new Purchase();
				purchaseProduct.setUserId(userId);
				purchaseProduct.setProductId(productId);
				purchaseProduct.setProductName(productResponse.getData().getName());
				Purchase saved = purchaseRepo.save(purchaseProduct);
				if (saved != null) {
					response.setData(1);
					response.setSuccess(true);
					response.setSuccessMessages(Constants.ORDER_PLACED);
				} else {
					response.setData(2);
					response.setSuccess(false);
					response.setSuccessMessages(Constants.ORDER__NOT_PLACED);
				}
				response.setStatus(HttpStatusCodes.OK);
			} else {
				response.setData(0);
				response.setSuccess(false);
				response.setSuccessMessages(Constants.PRODUCT_NOT_AVAILABLE);
				response.setStatus(HttpStatusCodes.NOT_FOUND);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			response.setData(null);
			response.setSuccessMessages(Constants.ERROR);
			response.setStatus(HttpStatusCodes.INTERNAL_SERVER_ERROR);
			response.setSuccess(false);
		}
		return response;
	}

}
