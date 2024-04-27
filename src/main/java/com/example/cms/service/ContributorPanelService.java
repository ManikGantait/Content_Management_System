package com.example.cms.service;

import org.springframework.http.ResponseEntity;

import com.example.cms.responsedto.ContributorPanelResponseEntity;
import com.example.cms.utility.ResponseStructure;

public interface ContributorPanelService {

	ResponseEntity<ResponseStructure<ContributorPanelResponseEntity>> addContributor(int userId, int panelId);

	ResponseEntity<ResponseStructure<ContributorPanelResponseEntity>> deleteContributor(int userId, int panelId);

}
