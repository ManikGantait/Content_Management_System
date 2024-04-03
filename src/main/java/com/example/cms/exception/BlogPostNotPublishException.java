package com.example.cms.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BlogPostNotPublishException extends RuntimeException {
	private String message;

}
