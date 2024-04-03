package com.example.cms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.cms.requestdto.PublishRequestEntity;
import com.example.cms.responsedto.PublishResponseEntity;
import com.example.cms.service.PublishService;
import com.example.cms.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class PublishController {
	
	private PublishService publishService;
	
	@PostMapping("/blog-posts/{postId}/publishes")
	public ResponseEntity<ResponseStructure<PublishResponseEntity>> publishBlogPost(@PathVariable int postId, @RequestBody PublishRequestEntity publishRequestEntity)
	{
		return publishService.publishBlogPost(postId, publishRequestEntity);
	}
	
	@PutMapping("/blog-posts/{postId}/publishes")
	public ResponseEntity<ResponseStructure<PublishResponseEntity>> unPublishBlogPost(@PathVariable int postId)
	{
		return publishService.unPublishBlogPost(postId);
	}

}
