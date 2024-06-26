package com.example.cms.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity.RequestMatcherConfigurer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;

import lombok.AllArgsConstructor;

/**
 *  7th for telling Spring to invoked the CustomUserDetailsService method to 
 *  provide our own security configuration
 *  But AuthenticationProvider does n't store data without encryption
 *  for that we are creating a bean of BCryptPasswordEncoder and passing to 
 *  AuthenticationProvider
 */
@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {
	
	private CustomUserDetailsService userDetailsService;
	
	@Bean
	AuthenticationProvider authenticationProvider() //Perform Database Authentication
	{
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder());
		provider.setUserDetailsService(userDetailsService);
		return provider;

		
	}
	
	@Bean
	PasswordEncoder passwordEncoder()
	{
		//Encrypt a particular String using BCrypt algorithm
		return new BCryptPasswordEncoder(12);
	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
	{	
		/**
		 * csrf-> Cross-site Request Forgery
		 * we are authorize the specified url using requestMatchers if the url match the permitAll
		 * if request url is not specified url the users should be authenticated.
		 * 
		 */
		return http.csrf(csrf->csrf.disable())
				.authorizeHttpRequests((auth)->{
					auth.requestMatchers("/users/register").permitAll();
					auth.requestMatchers("/blog-posts/{postId}/public").permitAll();
					auth.anyRequest().authenticated();
				})
				
				.formLogin(Customizer.withDefaults()).build();
	}
	
	
	
	
	
	

}
