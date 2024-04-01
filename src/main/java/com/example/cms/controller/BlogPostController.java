package com.example.cms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.cms.requestdto.BlogPostRequestEntity;
import com.example.cms.responsedto.BlogPostResponseEntity;
import com.example.cms.service.BlogPostService;
import com.example.cms.utility.ResponseStructure;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class BlogPostController {
	
	private BlogPostService  blogPostService;
	
	@PostMapping("/blogs/{blogId}/blog-posts")
	public ResponseEntity<ResponseStructure<BlogPostResponseEntity>> createDraft(@PathVariable int blogId,@RequestBody @Valid BlogPostRequestEntity blogPostRequestEntity)
	{
		return blogPostService.createDraft(blogId,blogPostRequestEntity);
	}

}
