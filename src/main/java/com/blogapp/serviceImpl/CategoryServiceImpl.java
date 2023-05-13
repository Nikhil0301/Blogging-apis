package com.blogapp.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogapp.entities.Category;
import com.blogapp.entities.User;
import com.blogapp.exceptions.ResourceNotFoundException;
import com.blogapp.payloads.CategoryDto;
import com.blogapp.repositories.CategoryRepo;
import com.blogapp.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private ModelMapper mapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category category = categoryDtoToCategory(categoryDto);
		Category savedCategory = categoryRepo.save(category);
		CategoryDto savedCategoryDto = categoryToCategoryDto(savedCategory);
		return savedCategoryDto;
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		// Category category = categoryDtoToCategory(categoryDto);
		System.out.println("in category update ..");
		Category category = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category", "categoryId", categoryId));
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		Category updatedCategory = categoryRepo.save(category);
		System.out.println(updatedCategory);
		CategoryDto updatedCategoryDto = categoryToCategoryDto(updatedCategory);
		return updatedCategoryDto;
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		Category category = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category", "categoryId", categoryId));
		categoryRepo.delete(category);
	}

	@Override
	public List<CategoryDto> getCategories() {
		List<CategoryDto> categories = new ArrayList<>();
		categoryRepo.findAll().forEach((category) -> {
			categories.add(categoryToCategoryDto(category));
		});
		return categories;
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		Category category = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category", "categoryId", categoryId));
		return categoryToCategoryDto(category);
	}

	// helping methods to convert DTO objects to entity object
	private Category categoryDtoToCategory(CategoryDto categoryDto) {
		Category category = mapper.map(categoryDto, Category.class);
		return category;
	}

	private CategoryDto categoryToCategoryDto(Category category) {
		CategoryDto categoryDto = mapper.map(category, CategoryDto.class);
		return categoryDto;
	}

}
