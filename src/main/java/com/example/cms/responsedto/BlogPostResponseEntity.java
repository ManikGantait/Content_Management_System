package com.example.cms.responsedto;

import java.time.LocalDateTime;

import com.example.cms.enums.PostType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlogPostResponseEntity {
	
	private int postId;
	private  String title;
	private String subTitle;
	private String summary;
	private PostType postType;
	
	
	private int  blogId;	
	private String createdBy;
	private LocalDateTime createAt;
	private LocalDateTime lastModifiedAt;
	private String lastModifiedBy;
	
	private PublishResponseEntity publishResponseEntity;
	
}
