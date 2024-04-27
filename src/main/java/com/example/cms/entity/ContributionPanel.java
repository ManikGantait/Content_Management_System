package com.example.cms.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class ContributionPanel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int panelId;
	
	
	@ManyToMany
	private List<User> contributors=new ArrayList<>();
	
	

}
