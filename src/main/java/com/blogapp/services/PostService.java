package com.blogapp.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.blogapp.entities.Post;
import com.blogapp.payloads.PostDto;
import com.blogapp.payloads.PostResponse;

public interface PostService {
	//create post 
	PostDto createPost(PostDto postDto , Integer userId , Integer categoryId);
	
	//update post by category
	PostDto updatePostByCategory(PostDto postDto, Integer categoryId , Integer postId);
	
	//update post By postId
	PostDto updatePost(PostDto postDto , Integer postId);
	
	//delete post
	void deletePost(Integer id);
	
	//get post by id
	PostDto getPostById(Integer id);
	
	//get all posts
	List<PostDto> getAllPosts();
	//get all
	PostResponse  _getAllPosts(Integer pageNumber , Integer pageSize , String sortBy , String sortDir);
	//get all post by category
	List<PostDto> getPostsByCategory(Integer CategorId);
	
	//get all post by user
	List<PostDto> getPostsByUser(Integer userId);
	
	//get post by date published
	
	//get post by keyword
	
	//get all posts of particular category of particular user 
	
	//search post by title or keyword
	List<PostDto> searchPosts(String title);
	
}
