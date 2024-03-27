package com.example.cms.service;

import org.springframework.http.ResponseEntity;


import com.example.cms.requestdto.UserRequestEntity;
import com.example.cms.responsedto.UserResponseEntity;
import com.example.cms.utility.ResponseStructure;


public interface UserService {

	public abstract ResponseEntity<ResponseStructure<UserResponseEntity>> registerUser(UserRequestEntity userRequest);

	public abstract ResponseEntity<ResponseStructure<UserResponseEntity>> softDeleteUser(int userId);


	

}
