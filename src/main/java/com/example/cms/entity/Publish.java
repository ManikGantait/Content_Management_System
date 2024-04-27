package com.example.cms.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Publish {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int publishId;
	private String scoTitle;
	private String scoDescription;
	private String scoTags;
	
	@OneToOne(mappedBy = "publish")
	private BlogPost blogPost;
	
	@OneToOne
	private Schedule schedule;

}
