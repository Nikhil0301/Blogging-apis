package com.blogapp.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.blogapp.entities.Category;
import com.blogapp.entities.Post;
import com.blogapp.entities.User;
import com.blogapp.payloads.PostDto;

public interface PostRepo extends JpaRepository<Post, Integer>{
	//Implements required custom finder methods
	List<Post> findByUser(User user);
	
	List<Post> findByCategory(Category category);
	
	//
	List<Post> findByTitleContaining(String title);
	
	
	//@Query(name = "select p from post p where p.title LIKE :key")
	//List<Post> findByTitleContaining(@Param("key") String title);
}
