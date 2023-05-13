package com.blogapp.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class CategoryDto {
	
	private Integer categoryId;
	
	@NotBlank
	@Size(min = 1 , max = 50)
	private String categoryTitle;
	
	@Size(max = 200)
	private String categoryDescription;
}
