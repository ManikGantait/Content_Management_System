package com.example.cms.serviceimpl;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.cms.entity.BlogPost;
import com.example.cms.entity.Publish;
import com.example.cms.enums.PostType;
import com.example.cms.exception.BlogNotFoundByIdException;
import com.example.cms.exception.BlogPostNotFoundByIdException;
import com.example.cms.exception.BlogPostNotPublishException;
import com.example.cms.exception.IllegalAccessRequestException;
import com.example.cms.repository.BlogPostRepository;
import com.example.cms.repository.ContributorPanelRepository;
import com.example.cms.repository.PublishRepository;
import com.example.cms.requestdto.PublishRequestEntity;
import com.example.cms.responsedto.PublishResponseEntity;
import com.example.cms.service.PublishService;
import com.example.cms.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PublishServiceImpl implements PublishService{
	
	private PublishRepository publishRepository;
	private BlogPostRepository blogPostRepository;
	private ContributorPanelRepository contributorPanelRepository;
	private ResponseStructure<PublishResponseEntity> responseStructure;
	
	private Publish mapToPublish(Publish publish, PublishRequestEntity  publishRequestEntity)
	{
		publish.setScoTitle(publishRequestEntity.getScoTitle());
		publish.setScoDescription(publishRequestEntity.getScoDescription());
		publish.setScoTags(publishRequestEntity.getScoTags());
		return publish;
	}
	private PublishResponseEntity mapToPublishResponseEntity(Publish publish)
	{
		return PublishResponseEntity.builder().publishId(publish.getPublishId()).scoTitle(publish.getScoTitle())
					.scoDescription(publish.getScoDescription())
					.scoTags(publish.getScoTags())
					.build();
	}

	@Override
	public ResponseEntity<ResponseStructure<PublishResponseEntity>> publishBlogPost(int postId,
			PublishRequestEntity publishRequestEntity) {
		return blogPostRepository.findById(postId).map(blogPost ->{
			String email=SecurityContextHolder.getContext().getAuthentication().getName();
			
			if(!blogPost.getCreatedBy().equals(email)&& !blogPost.getBlog().getUser().getEmail().equals(email))
				throw new IllegalAccessRequestException("Access Denied");
			
			if(blogPost.getPostType().equals(PostType.DRAFT))
			{
				Publish publish = publishRepository.save(mapToPublish(new Publish(), publishRequestEntity));
				
				publish.setBlogPost(blogPost);
				blogPost.setPostType(PostType.PUBLISHED);
				blogPost.setPublish(publish);
				blogPostRepository.save(blogPost);
				return ResponseEntity.ok(responseStructure.setStatusCode(HttpStatus.OK.value())
						.setMessgae("Published")
						.setData(mapToPublishResponseEntity(publishRepository.save(publish))));
			}
			else {
				
				Publish publish =mapToPublish(blogPost.getPublish(), publishRequestEntity);
				
				return ResponseEntity.ok(responseStructure.setStatusCode(HttpStatus.OK.value())
						.setMessgae("Already Published")
						.setData(mapToPublishResponseEntity(publishRepository.save(publish))));
				
			}
			 
			
		}).orElseThrow(()->new BlogPostNotFoundByIdException("Blog Not Found"));
		
	}
	@Override
	public ResponseEntity<ResponseStructure<PublishResponseEntity>> unPublishBlogPost(int postId) {
		
		return blogPostRepository.findById(postId).map(blogPost ->{
			
			String email=SecurityContextHolder.getContext().getAuthentication().getName();			
			if(!blogPost.getCreatedBy().equals(email)&& !blogPost.getBlog().getUser().getEmail().equals(email))
				throw new IllegalAccessRequestException("Access Denied");
			
			if(blogPost.getPostType().equals(PostType.DRAFT))
			throw new BlogPostNotPublishException("Blog Post Is not published yet");
			else {
				
				blogPost.setPostType(PostType.DRAFT);
				BlogPost blogPost2 = blogPostRepository.save(blogPost);
				
				return ResponseEntity.ok(responseStructure.setStatusCode(HttpStatus.OK.value())
						.setMessgae("UnPublished ")
						.setData(mapToPublishResponseEntity(blogPost2.getPublish())));			
			}
			 
			
		}).orElseThrow(()->new BlogPostNotFoundByIdException("Blog Not Found"));
	
	}
	

}
