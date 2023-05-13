package com.blogapp;

import java.lang.reflect.Field;
import java.util.Date;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.blogapp.payloads.PostDto;

@SpringBootApplication
public class BlogAppApisApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
//	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
//		PostDto postDto = new PostDto();
//		postDto.setAddedDate(new Date());
//		postDto.setContent("abc");
//		postDto.setTitle("Alpha");
//		Class c = postDto.getClass();
//		Field fields[] = c.getDeclaredFields();
//		for(Field field : fields) {
//			System.out.println(field.getName() + " , "+field.get(postDto));
//		}
//	}

}
