package com.example.cms.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BlogPostNotFoundByIdException extends RuntimeException {
	
	private String message;

}
