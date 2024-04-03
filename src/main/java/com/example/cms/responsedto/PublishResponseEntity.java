package com.example.cms.responsedto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class PublishResponseEntity {

	private int publishId;
	private String scoTitle;
	private String scoDescription;
	private String scoTags;
}
