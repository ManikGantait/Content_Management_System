package com.example.cms.service;

import org.springframework.http.ResponseEntity;

import com.example.cms.requestdto.BlogPostRequestEntity;
import com.example.cms.responsedto.BlogPostResponseEntity;
import com.example.cms.utility.ResponseStructure;

public interface BlogPostService {

	ResponseEntity<ResponseStructure<BlogPostResponseEntity>> createDraft(int blogId,BlogPostRequestEntity blogPostRequestEntity);
	

}
