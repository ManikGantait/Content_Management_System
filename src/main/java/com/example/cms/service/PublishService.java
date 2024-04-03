package com.example.cms.service;

import org.springframework.http.ResponseEntity;

import com.example.cms.requestdto.PublishRequestEntity;
import com.example.cms.responsedto.PublishResponseEntity;
import com.example.cms.utility.ResponseStructure;

public interface PublishService {

	ResponseEntity<ResponseStructure<PublishResponseEntity>> publishBlogPost(int postId,
			PublishRequestEntity publishRequestEntity);

	ResponseEntity<ResponseStructure<PublishResponseEntity>> unPublishBlogPost(int postId);

}
