package com.example.cms.serviceimpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.cms.entity.Blog;
import com.example.cms.entity.ContributionPanel;
import com.example.cms.entity.User;
import com.example.cms.exception.BlogAlreadyExistByTitle;
import com.example.cms.exception.BlogNotFoundByIdException;
import com.example.cms.exception.TopicNotSpecifiedException;
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
	private ResponseStructure<Boolean> responseStructure2;

	private Blog mapToBlog(Blog blog, BlogRequestEntity blogRequestEntity) {
		blog.setTitle(blogRequestEntity.getTitle());
		blog.setTopics(blogRequestEntity.getTopics());
		blog.setAbout(blogRequestEntity.getAbout());
		blog.getContributionPanel().setBlog(blog);
		return blog;
	}

	private BlogResponseEntity mapToBlogResponseEntity(Blog blog) {
		return BlogResponseEntity.builder().blogId(blog.getBlogId())
				.title(blog.getTitle())
				.about(blog.getAbout())
				.topics(blog.getTopics())
				.adminId(blog.getUser().getUserId())
				.build();

	}

	@Override
	public ResponseEntity<ResponseStructure<BlogResponseEntity>> createBlog(int userId,
			BlogRequestEntity blogRequestEntity) {

		Blog saveBlog = userRepository.findById(userId).map(user -> {
			
			if (blogRepository.existsByTitle(blogRequestEntity.getTitle()))
				throw new BlogAlreadyExistByTitle("Blog already exist with same title");
			
			if (blogRequestEntity.getTopics().length < 1)
				throw new TopicNotSpecifiedException("faild to create Blog");

			Blog blog = mapToBlog(new Blog(), blogRequestEntity);
			
			blog.setUser(user);
			return blogRepository.save(blog);
						

		}).orElseThrow(() -> new UserNotFoundByIdException("User With Specified Id not Found"));

		return ResponseEntity.ok(responseStructure.setStatusCode(HttpStatus.OK.value())
				.setMessgae("Blog created successfully").setData(mapToBlogResponseEntity(saveBlog)));

	}

	@Override
	public ResponseEntity<ResponseStructure<Boolean>> checkBlogTitleAvailability(String title) {
		if (blogRepository.existsByTitle(title))
			return ResponseEntity.ok(
					responseStructure2.setStatusCode(HttpStatus.OK.value()).setMessgae("Title Found").setData(true));
		else
			return ResponseEntity.ok().body(responseStructure2.setStatusCode(HttpStatus.OK.value())
					.setMessgae("Title Not Found").setData(false));
	}

	@Override
	public ResponseEntity<ResponseStructure<BlogResponseEntity>> findByBlogId(int blogId) {

		return blogRepository.findById(blogId).map(blog -> {
			return ResponseEntity.ok(responseStructure.setStatusCode(HttpStatus.OK.value()).setMessgae("Blog Found")
					.setData(mapToBlogResponseEntity(blog)));
		}).orElseThrow(() -> new BlogNotFoundByIdException("Blog is not present with specified Id"));

	}

	@Override
	public ResponseEntity<ResponseStructure<BlogResponseEntity>> updateBlogData(int blogId,
			BlogRequestEntity blogRequestEntity) {

		return blogRepository.findById(blogId).map(blog->{
			if(!blog.getTitle().equals(blogRequestEntity.getTitle()))
			if(blogRepository.existsByTitle(blogRequestEntity.getTitle()))
				throw new BlogAlreadyExistByTitle("Blog already exist by title unable to update");
			if(blogRequestEntity.getTopics().length<1)
				throw new TopicNotSpecifiedException("Specify the topic");
			Blog saveBlog = blogRepository.save(mapToBlog(blog, blogRequestEntity));
			return ResponseEntity.ok(responseStructure.setStatusCode(HttpStatus.OK.value())
														.setMessgae("Updated Successfully")
														.setData(mapToBlogResponseEntity(saveBlog)));
			
			
		}).orElseThrow(()->new BlogNotFoundByIdException("Blog with specified id is not present"));

	}

}
