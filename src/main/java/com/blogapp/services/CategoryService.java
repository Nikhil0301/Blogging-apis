package com.blogapp.services;

import java.util.List;

import com.blogapp.payloads.CategoryDto;

public interface CategoryService {
	//create category
	CategoryDto createCategory(CategoryDto category);
	
	
	//update category
	CategoryDto updateCategory(CategoryDto category , Integer categoryId);
	
	//delete category
	void deleteCategory(Integer id);
	
	//get all categories
	List<CategoryDto> getCategories();
	
	//get single category
	CategoryDto getCategory(Integer categoryId);
}
