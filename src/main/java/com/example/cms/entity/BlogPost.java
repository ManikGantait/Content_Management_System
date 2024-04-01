package com.example.cms.entity;


import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.cms.enums.PostType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
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
@EntityListeners(AuditingEntityListener.class)
public class BlogPost {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int postId;
	private  String title;
	private String subTitle;
	@Size(min = 500,max = 3000)
	private String summary;
	private PostType postType;
	
	
	@ManyToOne
	private Blog blog;
	
	@CreatedBy
	private String createdBy;
	@CreatedDate
	@Column(updatable = false)
	private LocalDateTime createAt;
	@LastModifiedDate
	private LocalDateTime lastModifiedAt;
	@LastModifiedBy
	private String lastModifiedBy;
	
	
	

}
