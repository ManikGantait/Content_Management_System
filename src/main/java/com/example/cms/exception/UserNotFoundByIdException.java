package com.example.cms.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@SuppressWarnings("serial")
@AllArgsConstructor
@Getter
public class UserNotFoundByIdException extends RuntimeException{
	private String message;

}
