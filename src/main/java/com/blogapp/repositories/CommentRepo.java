package com.blogapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogapp.entities.Category;
import com.blogapp.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer>{

}
