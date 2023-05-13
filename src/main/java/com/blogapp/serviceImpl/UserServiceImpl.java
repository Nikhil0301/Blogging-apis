package com.blogapp.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogapp.entities.User;
import com.blogapp.exceptions.ResourceNotFoundException;
import com.blogapp.payloads.UserDto;
import com.blogapp.repositories.UserRepo;
import com.blogapp.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo; 
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		User user = dtoToUser(userDto);
		User savedUser = userRepo.save(user);
		UserDto userDto1 = userToDto(savedUser);
		return userDto1;
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		Optional<User> user = userRepo.findById(userId);
		User user1 = null;
		User updatedUser = null;
		if(user.isPresent()) {
		    user1 = user.get();
		    user1.setName(userDto.getName());
		    user1.setEmail(userDto.getEmail());
		    user1.setPassword(userDto.getPassword());
		    user1.setAbout(userDto.getAbout());
			updatedUser = userRepo.save(user1);
		}else {
			throw new ResourceNotFoundException("user","id",userId);
		}
		return userToDto(updatedUser);
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user  = userRepo.findById(userId).
				            orElseThrow(() -> new ResourceNotFoundException("user","id",userId));
		return userToDto(user);
		
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users = userRepo.findAll();
		List<UserDto> UserDtos = users.stream().map(user -> userToDto(user)).collect(Collectors.toList());
		return UserDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user = userRepo.findById(userId).
				           orElseThrow(() -> new ResourceNotFoundException("user","id",userId));
		userRepo.delete(user);
	}
	
	private User dtoToUser(UserDto userDto) {
		User user = modelMapper.map(userDto, User.class);
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setPassword(userDto.getPassword());
//		user.setAbout(userDto.getAbout());
		return user;
	}
	
	private UserDto userToDto(User user) {
		UserDto userDto = modelMapper.map(user, UserDto.class);
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setPassword(user.getPassword());
//		userDto.setAbout(user.getAbout());
		return userDto;
	}

}
