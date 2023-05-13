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
import org.springframework.web.bind.annotation.RestController;

import com.blogapp.payloads.ApiResponse;
import com.blogapp.payloads.CategoryDto;
import com.blogapp.services.CategoryService;
import com.blogapp.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;

	// POST method creating category
	@PostMapping
	public ResponseEntity<CategoryDto> createCategory(@RequestBody @Valid CategoryDto categoryDto) {
		CategoryDto category = categoryService.createCategory(categoryDto);
		ResponseEntity<CategoryDto> response = new ResponseEntity(category, HttpStatus.CREATED);
		return response;
	}

	// PUT method updating category
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@RequestBody @Valid CategoryDto categoryDto,
			@PathVariable("categoryId") Integer categoryId) {
		CategoryDto category = categoryService.updateCategory(categoryDto, categoryId);
		return ResponseEntity.ok(category);

	}

	// GET method get one category
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getSingleCategory(@PathVariable("categoryId") Integer categoryId) {
		CategoryDto category = categoryService.getCategory(categoryId);
		return new ResponseEntity(category , HttpStatus.FOUND);
	}

	// DELETE method Delete category
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("categoryId") Integer categoryId) {
		categoryService.deleteCategory(categoryId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category deleted successfully!!!", true),
				HttpStatus.OK);
	}

	// GET method get all categories
	@GetMapping()
	public ResponseEntity<List<CategoryDto>> getCategories() {
		List<CategoryDto> categories = categoryService.getCategories();
		return new ResponseEntity(categories, HttpStatus.FOUND);
	}
}
