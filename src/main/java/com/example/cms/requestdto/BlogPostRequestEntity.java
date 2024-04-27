package com.example.cms.requestdto;



import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class BlogPostRequestEntity {
	
	@NotNull
	private  String title;
	private String subTitle;
	@Size(min = 500,message="Discription min 500 charater")
	private String summary;
	


}
