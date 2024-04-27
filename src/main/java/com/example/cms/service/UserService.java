package com.example.cms.service;

import org.springframework.http.ResponseEntity;


import com.example.cms.requestdto.UserRequestEntity;
import com.example.cms.responsedto.UserResponseEntity;
import com.example.cms.utility.ResponseStructure;

import jakarta.validation.Valid;

public interface UserService {

	public abstract ResponseEntity<ResponseStructure<UserResponseEntity>> registerUser(UserRequestEntity userRequest);

	public abstract ResponseEntity<ResponseStructure<UserResponseEntity>> softDeleteUser(int userId);
	public abstract ResponseEntity<ResponseStructure<UserResponseEntity>> findUniqueUser(int userId);


	

}
