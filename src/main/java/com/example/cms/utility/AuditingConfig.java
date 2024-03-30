package com.example.cms.utility;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;


@EnableJpaAuditing
@Configuration
public class AuditingConfig {
	
	@Bean
	AuditorAware<String> auditor()
	{
		 
		return () -> Optional.of(SecurityContextHolder
									.getContext()
									.getAuthentication().getName());
	}

}
