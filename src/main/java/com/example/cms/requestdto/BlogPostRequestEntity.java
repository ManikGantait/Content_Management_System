package com.example.cms.requestdto;

import com.example.cms.enums.PostType;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class BlogPostRequestEntity {
	
	@NotNull
	private  String title;
	private String subTitle;
	@Size(min = 500,message="Discription min 500 charater")
	private String summary;
	private PostType postType;
	private String scoTitle;
	private String scoDescription;
	private String[] scoTopics;

}
