package com.example.cms.requestdto;



import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class BlogRequestEntity {
	
	@Pattern(regexp = "^[a-zA-Z]*$",message = "title should be alphabate")
	private String title;
	@NotNull(message = "topics should not be empty ")
	private String[] topics;
	private String about;
	
	


}
