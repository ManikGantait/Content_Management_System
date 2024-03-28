package com.example.cms.serviceimpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.cms.entity.Blog;
import com.example.cms.entity.User;
import com.example.cms.exception.BlogAlreadyExistByTitle;
import com.example.cms.exception.UserNotFoundByIdException;
import com.example.cms.repository.BlogRepository;
import com.example.cms.repository.UserRepository;
import com.example.cms.requestdto.BlogRequestEntity;
import com.example.cms.responsedto.BlogResponseEntity;
import com.example.cms.service.BlogService;
import com.example.cms.utility.ResponseStructure;

import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class BlogServiceImpl implements BlogService {
	private UserRepository userRepository;
	private BlogRepository blogRepository;
	private ResponseStructure<BlogResponseEntity> responseStructure;
	
	private Blog mapToBlog(Blog blog,BlogRequestEntity blogRequestEntity)
	{
		blog.setTitle(blogRequestEntity.getTitle());
		blog.setTopics(blogRequestEntity.getTopics());
		blog.setAbout(blogRequestEntity.getAbout());
		return blog;
	}
	
	private BlogResponseEntity mapToBlogResponseEntity(Blog blog)
	{
		return BlogResponseEntity.builder().blogId(blog.getBlogId())
								.title(blog.getTitle())
								.about(blog.getAbout())
								.topics(blog.getTopics())
								.build();
		
	}

	@Override
	public ResponseEntity<ResponseStructure<BlogResponseEntity>> createBlog(int userId,BlogRequestEntity blogRequestEntity) {
	
		if(blogRepository.existsByTitle(blogRequestEntity.getTitle()))
			throw new BlogAlreadyExistByTitle("Blog already exist with same title");
		
		Blog saveBlog = userRepository.findById(userId).map(user->{
			
			 Blog blog = mapToBlog(new Blog(), blogRequestEntity);
			 blog.getUsers().add(user);
			 return blogRepository.save(blog);
			
		}).orElseThrow(()->new UserNotFoundByIdException("User With Specified Id not Found"));
			
		return ResponseEntity.ok(responseStructure.setStatusCode(HttpStatus.OK.value())
										.setMessgae("Blog created successfully")
										.setData(mapToBlogResponseEntity(saveBlog)));
		
	}

}
