package com.blogapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blogapp.payloads.ApiResponse;
import com.blogapp.payloads.PostDto;
import com.blogapp.payloads.PostResponse;
import com.blogapp.payloads.UserDto;
import com.blogapp.services.PostService;
import com.blogapp.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/")
public class PostController {
	@Autowired
	private PostService postService;

	// create Post POST method
	@PostMapping("/users/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody @Valid PostDto postDto, @PathVariable Integer userId,
			@PathVariable Integer categoryId) {
		PostDto createdPost = postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createdPost, HttpStatus.CREATED);
	}

	// get all posts of particular user
	@GetMapping("/users/{userId}/posts")
	public ResponseEntity<List<PostDto>> getAllPostOfUser(@PathVariable Integer userId) {
		System.out.println("in post controller in getAllPostOfUser method");
		List<PostDto> postsList = postService.getPostsByUser(userId);
		return new ResponseEntity<List<PostDto>>(postsList, HttpStatus.OK);
	}

	// get all posts of particular user
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getAllPostOfCategory(@PathVariable Integer categoryId) {
		List<PostDto> postsList = postService.getPostsByCategory(categoryId);
		return new ResponseEntity<List<PostDto>>(postsList, HttpStatus.OK);
	}

	// get all posts
//	@GetMapping("/posts")
//	public ResponseEntity<List<PostDto>> getAllPosts() {
//		List<PostDto> posts = postService.getAllPosts();
//		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
//	}
	
	// get all posts by pages
//	@GetMapping("/posts")
//	public ResponseEntity<PostResponse> getAllPosts(
//			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
//			@RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize) {
//		PostResponse postResponse = postService._getAllPosts(pageNumber, pageSize);
//		return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
//	}
//	
	// get all posts by sorting
		@GetMapping("/posts")
		public ResponseEntity<PostResponse> getAllPosts(
				@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
				@RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize,
				@RequestParam(value = "sortBy", defaultValue = "postId", required = false) String sortBy,
				@RequestParam(value = "sortDir", defaultValue = "ASC", required = false) String sortDir) {
			PostResponse postResponse = postService._getAllPosts(pageNumber, pageSize , sortBy , sortDir);
			return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
		}
	
	
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable @Valid Integer postId) {
		PostDto post = postService.getPostById(postId);
		return new ResponseEntity<PostDto>(post, HttpStatus.OK);
	}

	// Delete post DELETE method
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<?> deletePost(@PathVariable("postId") @Valid Integer postId) {
		postService.deletePost(postId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Post deleted successfully", true), HttpStatus.OK);
		// return new ResponseEntity(Map.of("message","user deleted successfully"),
		// HttpStatus.OK);
	}

	// update Post PUT method
	@PutMapping("/category/{categoryId}/posts/{postId}")
	public ResponseEntity<PostDto> updatePostByCategory(@RequestBody @Valid PostDto postDto, @PathVariable Integer categoryId,
			@PathVariable Integer postId) {
		PostDto updatedPost = postService.updatePostByCategory(postDto, categoryId, postId);
		return new ResponseEntity<PostDto>(updatedPost, HttpStatus.CREATED);
	}
	
	//search post by title or keyword
	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostDto> > searchPostByTitle(@PathVariable("keywords") String keywords) {
		List<PostDto> postDtos = postService.searchPosts(keywords);
		return new ResponseEntity<List<PostDto> >(postDtos, HttpStatus.OK);
	}
	

}
