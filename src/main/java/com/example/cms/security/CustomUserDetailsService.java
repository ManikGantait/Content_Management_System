package com.example.cms.security;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.cms.repository.UserRepository;

import lombok.AllArgsConstructor;

// 4th step to invoked the CustomUserDetails Class
@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService //fetching the user object 
{																	//to invoked CustomUserDetails(User)
	
	private UserRepository repository; //6th to get the user object from database 

	
//	5th implements the method for authentication and this method will call by AuthenticationProvider
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return repository.findByEmail(username).map(user->new CustomUserDetails(user))
				.orElseThrow(()->new UsernameNotFoundException("User Name is not Found"));
		
	}

}
