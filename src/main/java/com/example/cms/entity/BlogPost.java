package com.example.cms.entity;


import com.example.cms.enums.PostType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class BlogPost {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int postId;
	private  String title;
	private String subTitle;
	@Size(min = 500,max = 3000)
	private String summary;
	private PostType postType;
	private String scoTitle;
	private String scoDescription;
	private String[] scoTopics;
	
	@ManyToOne
	private Blog blog;  
	

}
