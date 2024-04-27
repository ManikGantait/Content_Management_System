package com.example.cms.service;

import org.springframework.http.ResponseEntity;

import com.example.cms.entity.Blog;
import com.example.cms.requestdto.BlogRequestEntity;
import com.example.cms.responsedto.BlogResponseEntity;
import com.example.cms.utility.ResponseStructure;

import jakarta.validation.Valid;

public interface BlogService {

	ResponseEntity<ResponseStructure<BlogResponseEntity>> createBlog(int userId, BlogRequestEntity blogRequestEntity);

	ResponseEntity<ResponseStructure<Boolean>> checkBlogTitleAvailability(String title);

	ResponseEntity<ResponseStructure<BlogResponseEntity>> findByBlogId(int blogId);

	ResponseEntity<ResponseStructure<BlogResponseEntity>> updateBlogData(int blogId,BlogRequestEntity blserogRequestEntity);

}
