package com.example.cms.serviceimpl;


import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.cms.entity.BlogPost;
import com.example.cms.entity.Publish;
import com.example.cms.entity.Schedule;
import com.example.cms.enums.PostType;
import com.example.cms.exception.BlogNotFoundByIdException;
import com.example.cms.exception.BlogPostNotFoundByIdException;
import com.example.cms.exception.BlogPostNotPublishException;
import com.example.cms.exception.IllegalAccessRequestException;
import com.example.cms.exception.ScheduleTimeNotValidException;
import com.example.cms.repository.BlogPostRepository;
import com.example.cms.repository.ContributorPanelRepository;
import com.example.cms.repository.PublishRepository;
import com.example.cms.repository.ScheduleRepository;
import com.example.cms.requestdto.PublishRequestEntity;
import com.example.cms.requestdto.ScheduleRequestEntity;
import com.example.cms.responsedto.PublishResponseEntity;
import com.example.cms.service.PublishService;
import com.example.cms.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PublishServiceImpl implements PublishService{
	
	private PublishRepository publishRepository;
	private BlogPostRepository blogPostRepository;
	private ResponseStructure<PublishResponseEntity> responseStructure;
	private ScheduleRepository scheduleRepository;
	
	private Publish mapToPublish(Publish publish, PublishRequestEntity  publishRequestEntity)
	{
				
		publish.setScoTitle(publishRequestEntity.getScoTitle());
		publish.setScoDescription(publishRequestEntity.getScoDescription());
		publish.setScoTags(publishRequestEntity.getScoTags());
		return publish;
	}
	private Schedule mapToSchedule(Schedule schedule, ScheduleRequestEntity scheduleRequestEntity) {
		
		schedule.setDateTime(scheduleRequestEntity.getDateTime());		
		return schedule;		
		
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
			
			Publish  publish;
			if(blogPost.getPublish()!=null)
			{
				publish=mapToPublish(blogPost.getPublish(), publishRequestEntity);
			}
			else {
				publish=mapToPublish(new Publish(), publishRequestEntity);
			}
			if(publishRequestEntity.getSchedule()!=null)
			{
				if(!publishRequestEntity.getSchedule().getDateTime().isAfter(LocalDateTime.now()))
					throw new ScheduleTimeNotValidException("time is not valid");
				if(blogPost.getPostType().equals(PostType.SCHEDULED))
				{
					System.err.println("+++++++++++++++++++++++++");
					publish.setSchedule(scheduleRepository.save(mapToSchedule(blogPost.getPublish().getSchedule(), publishRequestEntity.getSchedule())));
					
				}
				else
				{
					System.err.println("*****************************");
					publish.setSchedule(scheduleRepository.save(mapToSchedule(new Schedule(), publishRequestEntity.getSchedule())));
					blogPost.setPostType(PostType.SCHEDULED);
				}
			}else
			{
				blogPost.setPostType(PostType.PUBLISHED);
			}
			 publish.setBlogPost(blogPost);
			 blogPost.setPublish(publish);
			
			 publishRepository.save(publish);
			 blogPostRepository.save(blogPost);
			 return ResponseEntity.ok(responseStructure.setStatusCode(HttpStatus.OK.value())
						.setMessgae(""+blogPost.getPostType())
						.setData(mapToPublishResponseEntity(publish))
						);
							
			 
			
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
