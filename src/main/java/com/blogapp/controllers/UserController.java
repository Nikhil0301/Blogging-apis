package com.blogapp.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.blogapp.payloads.ApiResponse;
import com.blogapp.payloads.UserDto;
import com.blogapp.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	private UserService userService;

	// create user POST method
	@PostMapping
	public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto userDto) {
		UserDto createdUser = userService.createUser(userDto);
		return new ResponseEntity<UserDto>(createdUser, HttpStatus.CREATED);
	}
	
	// update user PUT method
	@RequestMapping(path = "/{userId}", method = RequestMethod.PUT)
	public ResponseEntity<UserDto> updateUser(@RequestBody @Valid UserDto userDto, @PathVariable("userId") @Valid Integer userId) {
		UserDto updatedUser = userService.updateUser(userDto, userId);
		return ResponseEntity.ok(updatedUser);
	}
	
	// get single user
	@RequestMapping(path = "/{userId}", method = RequestMethod.GET)
	public ResponseEntity<UserDto> getUserById(@PathVariable("userId") @Valid Integer userId) {
		UserDto user = userService.getUserById(userId);
		return new ResponseEntity<UserDto>(user, HttpStatus.FOUND);
	}
	
	// get all users GET method
	@GetMapping
	public ResponseEntity<List<UserDto>> getAllUsers() {
		List<UserDto> usersList = userService.getAllUsers();
		
		return new ResponseEntity<List<UserDto>>(usersList, HttpStatus.OK);
	}
	
	// Delete user DELETE method
	@DeleteMapping("/{userId}")
	public ResponseEntity<?> deleteUser(@PathVariable("userId") @Valid Integer userId) {
		userService.deleteUser(userId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("user deleted successfully", true), HttpStatus.OK);
		// return new ResponseEntity(Map.of("message","user deleted successfully"),
		// HttpStatus.OK);
	}
}
