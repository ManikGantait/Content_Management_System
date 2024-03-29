package com.example.cms.service;

import org.springframework.http.ResponseEntity;

import com.example.cms.responsedto.ContributerPanelResponseEntity;
import com.example.cms.utility.ResponseStructure;

public interface ContributorPanelService {

	ResponseEntity<ResponseStructure<ContributerPanelResponseEntity>> addContributor(int userId, int panelId);

	ResponseEntity<ResponseStructure<ContributerPanelResponseEntity>> deleteContributor(int userId, int panelId);

}
