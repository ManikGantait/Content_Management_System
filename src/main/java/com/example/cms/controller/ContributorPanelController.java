package com.example.cms.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cms.responsedto.ContributerPanelResponseEntity;
import com.example.cms.service.ContributorPanelService;
import com.example.cms.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class ContributorPanelController {
	
	private ContributorPanelService contributerPanelService;  
	
	

	@PutMapping("/users/{userId}/contribution-panels/{panelId}")
	public ResponseEntity<ResponseStructure<ContributerPanelResponseEntity>> addContributor(@PathVariable int userId, @PathVariable int panelId)
	{
		return contributerPanelService.addContributor(userId,panelId);
	}
	
	@DeleteMapping("/users/{userId}/contribution-panels/{panelId}")
	public ResponseEntity<ResponseStructure<ContributerPanelResponseEntity>> deleteContributer(@PathVariable int userId,@PathVariable int panelId)
	{
		return contributerPanelService.deleteContributor(userId,panelId);
	}
}
