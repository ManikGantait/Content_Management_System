package com.example.cms.responsedto;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlogResponseEntity {
	private int blogId;
	private String title;
	private String[] topics;
	private String about;

}
