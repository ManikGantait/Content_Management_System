package com.example.cms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.cms.requestdto.UserRequestEntity;
import com.example.cms.responsedto.UserResponseEntity;
import com.example.cms.service.UserService;
import com.example.cms.utility.ErrorStructure;
import com.example.cms.utility.ResponseStructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class UserController {
	
	private UserService service;	
	
	@Operation(description = "The End Point Is For User Registeration",responses= {
			@ApiResponse(responseCode = "200", description = "Registered Successfully"),
			@ApiResponse(responseCode = "404",description = "Failed To Registerd",
			              content = {
			            		  		@Content(schema=@Schema(implementation =ErrorStructure.class ))
									}
						)			
	})	
	@PostMapping("/users/register")
	public ResponseEntity<ResponseStructure<UserResponseEntity>> registerUser(@RequestBody @Valid UserRequestEntity userRequestEntity )
	{
		return service.registerUser(userRequestEntity);
	}

	
	
	@GetMapping("/test")
	public String test()
	{
		return "Hello From CMS";
	}

	@GetMapping("/users/{userId}")
	public ResponseEntity<ResponseStructure<UserResponseEntity>> findUniqueUser(@PathVariable int userId) {
		return service.findUniqueUser(userId);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
