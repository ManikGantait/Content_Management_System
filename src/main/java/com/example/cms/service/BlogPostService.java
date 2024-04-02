package com.example.cms.service;

import org.springframework.http.ResponseEntity;

import com.example.cms.requestdto.BlogPostRequestEntity;
import com.example.cms.responsedto.BlogPostResponseEntity;
import com.example.cms.utility.ResponseStructure;

import jakarta.validation.Valid;

public interface BlogPostService {

	ResponseEntity<ResponseStructure<BlogPostResponseEntity>> createDraft(int blogId,BlogPostRequestEntity blogPostRequestEntity);

	ResponseEntity<ResponseStructure<BlogPostResponseEntity>> updateDraft(int postId,BlogPostRequestEntity blogPostRequestEntity);

	ResponseEntity<ResponseStructure<BlogPostResponseEntity>> deleteDraft(int postId);
	

}
