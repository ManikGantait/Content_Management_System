package com.example.cms.serviceimpl;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.cms.entity.Blog;
import com.example.cms.entity.BlogPost;
import com.example.cms.entity.ContributionPanel;
import com.example.cms.entity.Publish;
import com.example.cms.enums.PostType;
import com.example.cms.exception.BlogNotFoundByIdException;
import com.example.cms.exception.BlogPostNotFoundByIdException;
import com.example.cms.exception.BlogPostNotPublishException;
import com.example.cms.exception.IllegalAccessRequestException;
import com.example.cms.exception.UserNotFoundByIdException;
import com.example.cms.repository.BlogPostRepository;
import com.example.cms.repository.BlogRepository;
import com.example.cms.repository.ContributorPanelRepository;
import com.example.cms.repository.UserRepository;
import com.example.cms.requestdto.BlogPostRequestEntity;
import com.example.cms.responsedto.BlogPostResponseEntity;
import com.example.cms.responsedto.PublishResponseEntity;
import com.example.cms.service.BlogPostService;
import com.example.cms.utility.AuditingConfig;
import com.example.cms.utility.ResponseStructure;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BlogPostServiceImpl implements BlogPostService {
	
	private BlogPostRepository blogPostRepository;
	private BlogRepository blogRepository;
	private ResponseStructure<BlogPostResponseEntity> responseStructure;
	private UserRepository userRepository;
	private ContributorPanelRepository contributorPanelRepository;
	
	private BlogPost mapToBlogPost(BlogPost blogPost, BlogPostRequestEntity blogPostRequestEntity) {
		blogPost.setTitle(blogPostRequestEntity.getTitle());
		blogPost.setSubTitle(blogPostRequestEntity.getSubTitle());
		blogPost.setSummary(blogPostRequestEntity.getSummary());
		blogPost.setPostType(PostType.DRAFT);
		
		return blogPost;
	}
	
	private BlogPostResponseEntity mapToBlogPostResponseEntity(BlogPost blogPost) {
		
		PublishResponseEntity mapToPublishResponseEntity=null;
		if(blogPost.getPublish()!=null) {
			mapToPublishResponseEntity = mapToPublishResponseEntity(blogPost.getPublish());
		}
		
		return BlogPostResponseEntity.builder().postId(blogPost.getPostId())
										.title(blogPost.getTitle())
										.subTitle(blogPost.getSubTitle())
										.summary(blogPost.getSummary())
										.postType(blogPost.getPostType())
										.blogId(blogPost.getBlog().getBlogId())
										.createdBy(blogPost.getCreatedBy())
										.createAt(blogPost.getCreateAt())
										.lastModifiedAt(blogPost.getLastModifiedAt())
										.lastModifiedBy(blogPost.getLastModifiedBy())
										.publishResponseEntity(mapToPublishResponseEntity)
										.build();
	}
	private PublishResponseEntity mapToPublishResponseEntity(Publish publish)
	{
		return PublishResponseEntity.builder().publishId(publish.getPublishId()).scoTitle(publish.getScoTitle())
					.scoDescription(publish.getScoDescription())
					.scoTags(publish.getScoTags())
					.build();
	}


	@Override
	public ResponseEntity<ResponseStructure<BlogPostResponseEntity>> createDraft(int blogId,BlogPostRequestEntity blogPostRequestEntity) {
		
		return blogRepository.findById(blogId).map(blog->{
			
			BlogPost blogPost = mapToBlogPost(new BlogPost(),blogPostRequestEntity);
			blogPost.setBlog(blog);
			
			
			return ResponseEntity.ok(responseStructure.setStatusCode(HttpStatus.OK.value())
					.setMessgae("BlogPost Created")
					.setData(mapToBlogPostResponseEntity( blogPostRepository.save(blogPost))));
			
		}).orElseThrow(()->new BlogNotFoundByIdException("Blog Not found By Id"));
		
		
		
	}

	@Override
	public ResponseEntity<ResponseStructure<BlogPostResponseEntity>> updateDraft(int postId,
			 BlogPostRequestEntity blogPostRequestEntity) {
		
		String email=SecurityContextHolder.getContext().getAuthentication().getName();
		
		return userRepository.findByEmail(email).map(owner->{
			
			return blogPostRepository.findById(postId).map(post->{
				Blog blog=post.getBlog();
				if(!blog.getUser().getEmail().equals(email) &&! contributorPanelRepository.existsByPanelIdAndContributors(blog.getContributionPanel().getPanelId(),owner) )
					throw new IllegalAccessRequestException("Access Denied");
				
				BlogPost blogPost = mapToBlogPost(post, blogPostRequestEntity);

				return ResponseEntity
						.ok(responseStructure.setStatusCode(HttpStatus.OK.value()).setMessgae("BlogPost Updated")
								.setData(mapToBlogPostResponseEntity(blogPostRepository.save(blogPost))));
				
				
			}).orElseThrow(()-> new BlogNotFoundByIdException("Blog not Found"));
			
		}).orElseThrow(()->new UserNotFoundByIdException("user Not found By Id"));
		
	}

	@Override
	public ResponseEntity<ResponseStructure<BlogPostResponseEntity>> deleteDraft(int postId) {
		
		
	return blogPostRepository.findById(postId).map(blogPost ->{
		String email=SecurityContextHolder.getContext().getAuthentication().getName();
		if(!blogPost.getCreatedBy().equals(email) && ! blogPost.getBlog().getUser().getEmail().equals(email))
			throw new IllegalAccessRequestException("Access Denied");
		blogPostRepository.delete(blogPost);		
		return ResponseEntity.ok(responseStructure.setStatusCode(HttpStatus.OK.value())
											.setMessgae("Deleted")
											.setData(mapToBlogPostResponseEntity(blogPost)));
		
	}).orElseThrow(()->new BlogPostNotFoundByIdException("Blog Post Not Found with specified id"));
		
	}

	@Override
	public ResponseEntity<ResponseStructure<BlogPostResponseEntity>> findBlogPostById(int postId) {
		return blogPostRepository.findById(postId).map(blogPost ->{
			return ResponseEntity.ok(responseStructure.setStatusCode(HttpStatus.OK.value())
					.setData(mapToBlogPostResponseEntity(blogPost))
					.setMessgae("Blog Found"));
			
		}).orElseThrow(()->new BlogPostNotFoundByIdException("Blog Post Not Found"));
		
	}

	@Override
	public ResponseEntity<ResponseStructure<BlogPostResponseEntity>> findBlogPostByPostType(int postId) {
		
		return blogPostRepository.findByPostIdAndPostType(postId,PostType.PUBLISHED).map(blogPost->
			ResponseEntity.ok(responseStructure.setStatusCode(HttpStatus.OK.value())
									.setMessgae("Blog Found")
									.setData(mapToBlogPostResponseEntity(blogPost)))
		).orElseThrow(()->new BlogPostNotFoundByIdException("Blog Not Found By Id"));
		
	}
	

	
	

}
