package com.example.cms.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BlogNotFoundByIdException extends RuntimeException {
	private String message;

}
