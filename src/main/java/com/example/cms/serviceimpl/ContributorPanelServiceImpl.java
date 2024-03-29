package com.example.cms.serviceimpl;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.cms.entity.ContributionPanel;
import com.example.cms.exception.ContributorPanelNotFoundByIdException;
import com.example.cms.exception.IllegalAccessRequestException;
import com.example.cms.exception.UserNotFoundByIdException;
import com.example.cms.repository.BlogRepository;
import com.example.cms.repository.ContributorPanelRepository;
import com.example.cms.repository.UserRepository;
import com.example.cms.responsedto.ContributerPanelResponseEntity;
import com.example.cms.service.ContributorPanelService;
import com.example.cms.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ContributorPanelServiceImpl implements ContributorPanelService {
	
	private ContributorPanelRepository contributorPanelRepository;
	private BlogRepository blogRepository;
	private UserRepository userRrepository;
	private ResponseStructure<ContributerPanelResponseEntity> responseStructure;

	@Override
	public ResponseEntity<ResponseStructure<ContributerPanelResponseEntity>> addContributor(int userId, int panelId) {

		String email=SecurityContextHolder.getContext().getAuthentication().getName();
		
		ContributionPanel contributionPanel = userRrepository.findByEmail(email).map(owner -> {
			
			return contributorPanelRepository.findById(panelId).map(panel -> {
				if (!blogRepository.existsByUserAndContributionPanel(owner, panel))
					throw new IllegalAccessRequestException("Access denied");
				return userRrepository.findById(userId).map(user -> {
					panel.getContributors().add(user);
					return contributorPanelRepository.save(panel);

				}).orElseThrow(() -> new UserNotFoundByIdException("user not Found"));

			}).orElseThrow(() -> new UserNotFoundByIdException("User Not Found With Specified Id"));

		}).get();
		
		
		return ResponseEntity.ok(responseStructure.setStatusCode(HttpStatus.OK.value())
													.setMessgae("Contributor added")
													.setData(mapToContributerPanelResponseEntity(contributionPanel)));
		
		
	}


	private ContributerPanelResponseEntity mapToContributerPanelResponseEntity(ContributionPanel contributionPanel) {
		
		return ContributerPanelResponseEntity.builder()
												.panelId(contributionPanel.getPanelId()
									).build();
		

		
	}


	@Override
	public ResponseEntity<ResponseStructure<ContributerPanelResponseEntity>> deleteContributor(int userId,
			int panelId) {
	String email=SecurityContextHolder.getContext().getAuthentication().getName();
		
		ContributionPanel contributionPanel = userRrepository.findByEmail(email).map(owner -> {
			
			return contributorPanelRepository.findById(panelId).map(panel -> {
				
				if (!blogRepository.existsByUserAndContributionPanel(owner, panel))
					throw new IllegalAccessRequestException("Access Denied");
				return userRrepository.findById(userId).map(user -> {
					panel.getContributors().remove(user);
					return contributorPanelRepository.save(panel);

				}).orElseThrow(() -> new UserNotFoundByIdException("User Not Found With Specified Id"));

			}).orElseThrow(()->new ContributorPanelNotFoundByIdException("Contributor with specified id not found"));

		}).get();
		
		
		return ResponseEntity.ok(responseStructure.setStatusCode(HttpStatus.OK.value())
													.setMessgae("Contributor deleted")
													.setData(mapToContributerPanelResponseEntity(contributionPanel)));
		
	}
	
	
			
	
	

}
