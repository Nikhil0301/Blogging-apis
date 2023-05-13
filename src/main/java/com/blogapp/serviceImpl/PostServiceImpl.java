package com.blogapp.serviceImpl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blogapp.entities.Category;
import com.blogapp.entities.Post;
import com.blogapp.entities.User;
import com.blogapp.exceptions.ResourceNotFoundException;
import com.blogapp.payloads.CategoryDto;
import com.blogapp.payloads.PostDto;
import com.blogapp.payloads.PostResponse;
import com.blogapp.payloads.UserDto;
import com.blogapp.repositories.CategoryRepo;
import com.blogapp.repositories.PostRepo;
import com.blogapp.repositories.UserRepo;
import com.blogapp.services.PostService;

@Service
public class PostServiceImpl  implements PostService{

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public PostDto createPost(PostDto postDto , Integer userId , Integer categoryId) {
		
		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", "user id", userId));
		Category category = categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category", "categoryId", categoryId));
		Post post = postDtoToPost(postDto);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		
		Post savedPost = postRepo.save(post);
		PostDto postDto1 = this.postToPostDto(savedPost);
		return postDto1;
	}
	
	public PostDto updatePost(PostDto postDto , Integer postId) {
		Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post" , "postId" , postId));
		
		if(postDto.getCategory() != null) {
			Category category1 = mapper.map(postDto.getCategory(), Category.class);
			post.setCategory(category1);
		}
		if(postDto.getContent() != null) {
			post.setContent(postDto.getContent());
		}
		if(postDto.getImageName() != null) {
			post.setImageName(postDto.getImageName());
		}
		if(postDto.getTitle() != null) {
			post.setTitle(postDto.getTitle());
		}
		return postDto;
		
	}

	@Override
	public PostDto updatePostByCategory(PostDto postDto, Integer categoryId , Integer postId) {
		/*Doubt how to iterate over fields of class and set only non null
		 * values to another class
		 * */
		Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category" , "categoryId" , categoryId));
		List<Post> postList = this.postRepo.findByCategory(category);
		Post post = null;
		//null check of values to be updated
		for (Iterator iterator = postList.iterator(); iterator.hasNext();) {
			Post post2 = (Post) iterator.next();
			if(post2.getPostId() == postId) {
				post = post2;
				break;
			}
		}
		if(post == null)
			throw new ResourceNotFoundException("Post" , "postId" ,postId);
		if(postDto.getCategory() != null) {
			Category category1 = mapper.map(postDto.getCategory(), Category.class);
			post.setCategory(category1);
		}
		if(postDto.getContent() != null) {
			post.setContent(postDto.getContent());
		}
		if(postDto.getImageName() != null) {
			post.setImageName(postDto.getImageName());
		}
		if(postDto.getTitle() != null) {
			post.setTitle(postDto.getTitle());
		}
		
		Post updatedPost = postRepo.save(post);
		PostDto updatedPostDto = postToPostDto(updatedPost);
		return updatedPostDto;
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","postId",postId));
		postRepo.delete(post);
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Optional<Post> post = postRepo.findById(postId);
		if(post.isPresent())
			return mapper.map(post.get() , PostDto.class);
		throw new ResourceNotFoundException("Post","postId",postId);
		//return null;
	}

	@Override
	public List<PostDto> getAllPosts() {
		List<PostDto> postsList = postRepo
				                   .findAll()
				                   .stream()
				                   .map((post) -> this.mapper.map(post, PostDto.class))
				                   .collect(Collectors.toList());
		return postsList;
	}

	@Override
	public List<PostDto> getPostsByCategory(Integer categorId) {
		Category category = categoryRepo.findById(categorId)
				.orElseThrow(() -> new ResourceNotFoundException("category", "categoryId", categorId));
		List<Post> postList = postRepo.findByCategory(category);
		List<PostDto> postDtoList = new ArrayList<>();
		for(Post post: postList) {
			UserDto userDto = mapper.map(post.getUser(), UserDto.class);
			postDtoList.add(new PostDto(post.getPostId() , post.getTitle() , post.getContent() , post.getImageName() , post.getAddedDate() , userDto));
		}
		return postDtoList;
	}

	@Override
	public List<PostDto> getPostsByUser(Integer userId) {
		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", "user id", userId));
		List<Post> postList = postRepo.findByUser(user);
		
		//Using stream 
		List<PostDto> postDtos = postList.stream().map((post) -> this.mapper.map(postList , PostDto.class)).collect(Collectors.toList());
		
		List<PostDto> postDtoList = new ArrayList<>();
		for(Post post: postList) {
			CategoryDto categoryDto = mapper.map(post.getCategory(), CategoryDto.class);
			postDtoList.add(new PostDto(post.getPostId() , post.getTitle() , post.getContent() , post.getImageName() , post.getAddedDate() , categoryDto));
		}
		return postDtoList;
	}
	
	
	//helper methods
	Post postDtoToPost(PostDto postDto) {
		Post post = new Post();
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		return post;
	}
	
	PostDto postToPostDto(Post post) {
		PostDto postDto = new PostDto();
		postDto.setTitle(post.getTitle());
		postDto.setContent(post.getContent());
		postDto.setAddedDate(post.getAddedDate());
		postDto.setImageName(post.getImageName());
		//set userDto and categoryDto in PostDto
		UserDto userDto = mapper.map(post.getUser(), UserDto.class);
		CategoryDto categoryDto = mapper.map(post.getCategory(), CategoryDto.class);
		
		postDto.setUser(userDto);
		postDto.setCategory(categoryDto);
		return postDto;
	}
	
	public boolean checkNull() throws IllegalAccessException {
	    for (Field f : getClass().getDeclaredFields())
	        if (f.get(this) != null)
	            return false;
	    return true;            
	}

	@Override
	public PostResponse _getAllPosts(Integer pageNumber , Integer pageSize , String sortBy , String sortDir) {
		Sort sort = Sort.by(sortBy);
		if(sortDir.equalsIgnoreCase("desc"))
			sort = Sort.by(sortBy).descending();
		Pageable pageRequest = PageRequest.of(pageNumber , pageSize , sort);
		
		Page<Post> pagePost = postRepo.findAll(pageRequest);
		List<Post> allPosts = pagePost.getContent();
		List<PostDto> postDtos = allPosts.stream().map((post) -> mapper.map(post, PostDto.class)).collect(Collectors.toList());
		PostResponse postReponse  = new PostResponse();
		postReponse.setContent(postDtos);
		postReponse.setPageNumber(pagePost.getNumber());
		postReponse.setPageSize(pagePost.getSize());
		postReponse.setTotalElements(pagePost.getTotalPages());
		postReponse.setTotalPages(pagePost.getTotalPages());
		postReponse.setLastPage(pagePost.isLast());
		return postReponse;
	}
	
	public List<PostDto> searchPosts(String title){
		List<Post> allPosts = postRepo.findByTitleContaining(title);
		List<PostDto> postDtos  = allPosts.stream().map((post) -> mapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
		
	}
	
}
