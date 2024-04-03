package com.example.cms.serviceimpl;


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
import com.example.cms.responsedto.ContributorPanelResponseEntity;
import com.example.cms.service.ContributorPanelService;
import com.example.cms.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ContributorPanelServiceImpl implements ContributorPanelService {
	
	private ContributorPanelRepository contributorPanelRepository;
	private BlogRepository blogRepository;
	private UserRepository userRrepository;
	private ResponseStructure<ContributorPanelResponseEntity> responseStructure;
	

	private ContributorPanelResponseEntity mapToContributorPanelResponseEntity(ContributionPanel contributionPanel,String username ) {
		
		return ContributorPanelResponseEntity.builder()
												.panelId(contributionPanel.getPanelId())
												.username(username)
												.build();
		

		
	}


	@Override
	public ResponseEntity<ResponseStructure<ContributorPanelResponseEntity>> addContributor(int userId, int panelId) {

		String email=SecurityContextHolder.getContext().getAuthentication().getName();
		
		return  userRrepository.findByEmail(email).map(owner -> {
			
			return contributorPanelRepository.findById(panelId).map(panel -> {
				if (!blogRepository.existsByUserAndContributionPanel(owner, panel))
					throw new IllegalAccessRequestException("Access denied");
				return userRrepository.findById(userId).map(contributor -> {
					
					if(!panel.getContributors().contains(contributor))
					{
						panel.getContributors().add(contributor);
						return ResponseEntity.ok(responseStructure.setStatusCode(HttpStatus.OK.value())
													.setMessgae("Contributor added")
								.setData(mapToContributorPanelResponseEntity(panel, contributor.getUsername())));
					}
					return ResponseEntity.ok(responseStructure
							.setStatusCode(HttpStatus.OK.value())
							.setMessgae("Contributor already exist")
							.setData(mapToContributorPanelResponseEntity(panel, contributor.getUsername())));	
						
						

				}).orElseThrow(() -> new UserNotFoundByIdException("user not Found"));

			}).orElseThrow(() -> new UserNotFoundByIdException("User Not Found With Specified Id"));

		}).get();
		
		

		
		
		
	}



	@Override
	public ResponseEntity<ResponseStructure<ContributorPanelResponseEntity>> deleteContributor(int userId,
			int panelId) {
	String email=SecurityContextHolder.getContext().getAuthentication().getName();
		
		return userRrepository.findByEmail(email).map(owner -> {
			
			return contributorPanelRepository.findById(panelId).map(panel -> {
				
				if (!blogRepository.existsByUserAndContributionPanel(owner, panel))
					throw new IllegalAccessRequestException("Access Denied");
				return userRrepository.findById(userId).map(user -> {
					panel.getContributors().remove(user);
					return ResponseEntity.ok(responseStructure.setStatusCode(HttpStatus.OK.value())
							.setMessgae("Contributor deleted")
							.setData(mapToContributorPanelResponseEntity(contributorPanelRepository.save(panel), user.getUsername())));

				}).orElseThrow(() -> new UserNotFoundByIdException("User Not Found With Specified Id"));

			}).orElseThrow(()->new ContributorPanelNotFoundByIdException("Contributor with specified id not found"));

		}).get();
		
		
		
		
	}
	
	
			
	
	

}
