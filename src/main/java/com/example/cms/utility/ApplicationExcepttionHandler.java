package com.example.cms.utility;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.cms.exception.BlogAlreadyExistByTitle;
import com.example.cms.exception.UserAlreadyExistByEmailException;
import com.example.cms.exception.UserNotFoundByIdException;

import lombok.AllArgsConstructor;


@RestControllerAdvice
@AllArgsConstructor
public class ApplicationExcepttionHandler extends ResponseEntityExceptionHandler {
	
	
	private ErrorStructure<Object> errorStructure;
	private ErrorStructure<String> errorStructure2;

	private ResponseEntity<ErrorStructure<String>> errorStructure(HttpStatus httpStatus, String message, String rootcause)
	{
		return ResponseEntity.badRequest().body(errorStructure2.setStatusCode(httpStatus.value()).setMessage(message).setRootCause(rootcause));
	}
	
	private ResponseEntity<Object> errorStructure(HttpStatus httpStatus, String message, Object rootcause)
	{
		return ResponseEntity.badRequest().body(errorStructure.setStatusCode(httpStatus.value()).setMessage(message).setRootCause(rootcause));
	}
	
	
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		List<ObjectError> errors = ex.getAllErrors();

		Map<String, String> errorMap = new HashMap<>();
		errors.forEach((error) -> {
			FieldError fieldError = (FieldError) error;
			errorMap.put(fieldError.getField(), error.getDefaultMessage());
		});
		
		return errorStructure(HttpStatus.BAD_REQUEST, "Invalid Input", errorMap);
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleUserAlreadyExistByEmail(UserAlreadyExistByEmailException ex)
	{
		return errorStructure(HttpStatus.BAD_REQUEST,ex.getMessage() ,"User Is Already exists with same email");
	}
	
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleUsernameNotFound(UsernameNotFoundException ex)
	{
		return errorStructure(HttpStatus.BAD_REQUEST,ex.getMessage(), "User Name Not Found");
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleUserNotFoundById(UserNotFoundByIdException ex)
	{
		return errorStructure(HttpStatus.BAD_REQUEST, ex.getMessage(), "User Id Not Found");
	}

	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleBlogAlreadyExistByTitle(BlogAlreadyExistByTitle ex)
	{
		return errorStructure(HttpStatus.BAD_REQUEST, ex.getMessage(), "Duplicate Title");
	}
	

	
		
	
}
