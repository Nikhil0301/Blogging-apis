package com.blogapp.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.blogapp.entities.Comment;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PostDto {
	
	public PostDto(Integer postId2, String title2, String content2, String imageName2, Date addedDate2, UserDto userDto) {
		this.postId = postId2;
		this.title = title2;
		this.content = content2;
		this.imageName = imageName2;
		this.addedDate = addedDate2;
		this.user = userDto;
	}
	public PostDto(Integer postId2, String title2, String content2, String imageName2, Date addedDate2, CategoryDto categoryDto) {
		this.postId = postId2;
		this.title = title2;
		this.content = content2;
		this.imageName = imageName2;
		this.addedDate = addedDate2;
		this.category = categoryDto;
	}

	//@JsonIgnore
	private Integer postId;
	
	private String title;
	
	private String content;
	
	private String imageName;
	
	private Date addedDate;
//	
	private UserDto user;
//	
	private CategoryDto category;
	
	private Set<CommentDto> comments = new HashSet<>();
}
