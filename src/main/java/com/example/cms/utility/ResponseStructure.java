package com.example.cms.utility;

import org.springframework.stereotype.Component;

@Component
public class ResponseStructure<T> {
	
	private int statusCode;
	private String message;
	private T data;
	
	public int getStatusCode() {
		return statusCode;
	}
	public ResponseStructure<T> setStatusCode(int statusCode) {
		this.statusCode = statusCode;
		return this;
	}
	public String getMessgae() {
		return message;
	}
	public ResponseStructure<T> setMessgae(String messgae) {
		this.message = messgae;
		return this;
	}
	public T getData() {
		return data;
	}
	public ResponseStructure<T> setData(T data) {
		this.data = data;
		return this;
	}
	
	

}
