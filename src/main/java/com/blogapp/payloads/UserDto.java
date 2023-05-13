package com.blogapp.payloads;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserDto {
	
	private int id;
	
	@NotEmpty
	@Size(min = 4 , message = "username must be of min 4 character!")
	private String name;
	
	@Email(message = "email address is not valid")
	@NotEmpty(message = "email must not empty")
	private String email;
	
	@NotEmpty(message = "password must not empty")
	@Size(min = 3 , max = 10 , message = "password must be min of 3 chars and maximum of 10 chars!! ")
	private String password;
	
	
	private String about;
}