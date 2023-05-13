package com.blogapp.services;

import com.blogapp.payloads.CommentDto;

public interface CommentService {
	CommentDto createComment(CommentDto commentDto , Integer postID);
	
	void deleteComment(Integer commentId);
}
