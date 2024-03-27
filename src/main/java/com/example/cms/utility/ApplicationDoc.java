package com.example.cms.utility;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
@OpenAPIDefinition
public class ApplicationDoc {
	
	@Bean
	Contact contact()
	{
		return new Contact().name("Manik")
							.email("manikgantait2001@gmail.com")
							.url("xyz.com");
	}
	
	@Bean
	Info info()
	{
		return new Info().title("Content Management System")
				.description("Content Management System Using RESTfull API")
				.contact(contact());
	}
	@Bean
	OpenAPI openAPI()	
	{
		return new OpenAPI().info(info());
	}

}
