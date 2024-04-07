package com.example.cms.requestdto;

import com.example.cms.entity.Schedule;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PublishRequestEntity {
	
	@NotNull
	private String scoTitle;
	private String scoDescription;
	private String scoTags;
	
	
	private ScheduleRequestEntity  schedule;

}
