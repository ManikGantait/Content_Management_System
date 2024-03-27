package com.example.cms.serviceimpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.cms.entity.User;
import com.example.cms.exception.UserAlreadyExistByEmailException;
import com.example.cms.exception.UserNotFoundByIdException;
import com.example.cms.repository.UserRepository;
import com.example.cms.requestdto.UserRequestEntity;
import com.example.cms.responsedto.UserResponseEntity;
import com.example.cms.service.UserService;
import com.example.cms.utility.ResponseStructure;

import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService
{
	private UserRepository repository;
	private ResponseStructure<UserResponseEntity> structure;
	private ResponseStructure<String> structure2;
	private PasswordEncoder passwordEncoder;
	
	
	private UserResponseEntity mapToUserResponse(User save) {
		return UserResponseEntity.builder().userId(save.getUserId())
		.username(save.getUsername())
		.email(save.getEmail())
		.createdAt(save.getCreatedAt())
		.lastModifiedAt(save.getLastModifiedAt()).build();		
	}
	private User mapToUser(User user, UserRequestEntity userRequest) {
		
		String encode = passwordEncoder.encode(userRequest.getPassword());
		
		user.setUsername(userRequest.getUsername());
		user.setEmail(userRequest.getEmail());
		user.setPassword(encode);
		user.setDeleted(false);
		return user;
	}	



	@Override
	public ResponseEntity<ResponseStructure<UserResponseEntity>> registerUser(UserRequestEntity userRequest) {
	
		if(repository.existsByEmail(userRequest.getEmail()))
			throw new UserAlreadyExistByEmailException("Faild To Register");
		
		User save = repository.save(mapToUser(new User(), userRequest));
		
		return ResponseEntity.ok(structure.setStatusCode(HttpStatus.OK.value())
				.setMessgae("Registered Successfully")
				.setData(mapToUserResponse(save)));		
	}
	
	
	@Override
	public ResponseEntity<ResponseStructure<UserResponseEntity>> softDeleteUser(int userId) {
		return repository.findById(userId).map(user->{
				user.setDeleted(true);
				return ResponseEntity.ok(structure.setStatusCode(HttpStatus.OK.value())
													.setMessgae("Deleted Successfully")
													.setData(mapToUserResponse(repository.save(user))));
		}).orElseThrow(()->new UserNotFoundByIdException("User is not found By Id"));
	}

	
	


	

	
	
	

}
