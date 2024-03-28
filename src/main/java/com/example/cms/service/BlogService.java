package com.example.cms.service;

import org.springframework.http.ResponseEntity;

import com.example.cms.entity.Blog;
import com.example.cms.requestdto.BlogRequestEntity;
import com.example.cms.responsedto.BlogResponseEntity;
import com.example.cms.utility.ResponseStructure;

public interface BlogService {

	ResponseEntity<ResponseStructure<BlogResponseEntity>> createBlog(int userId, BlogRequestEntity blogRequestEntity);

	ResponseEntity<ResponseStructure<Boolean>> checkBlogTitleAvailability(String title);

}
