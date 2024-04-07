package com.example.cms.utility;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.cms.entity.BlogPost;
import com.example.cms.entity.Schedule;
import com.example.cms.enums.PostType;
import com.example.cms.repository.BlogPostRepository;
import com.example.cms.repository.PublishRepository;
import com.example.cms.repository.ScheduleRepository;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ScheduledJobs {
	
	private BlogPostRepository blogPostRepository;

	
	@Scheduled(fixedDelay = 60*1000l)
	public void publishedSheduledBlogPost()
	{
		List<BlogPost> blogPosts=blogPostRepository.findAllByPublishScheduleDateTimeLessThanEqualAndPostType(LocalDateTime.now(),PostType.SCHEDULED)
				.stream().map(post->{
					post.setPostType(PostType.PUBLISHED);
					return post;
				}).collect(Collectors.toList());
		System.out.println(blogPosts);
		for(BlogPost b:blogPosts)
		{
			blogPostRepository.save(b);
		}
		
		
	}
	
	

}
