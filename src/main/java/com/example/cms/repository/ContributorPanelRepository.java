package com.example.cms.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cms.entity.ContributionPanel;
import com.example.cms.entity.User;

public interface ContributorPanelRepository extends JpaRepository<ContributionPanel, Integer> {

	boolean existsByPanelIdAndContributors(int panelId, User owner);



}
