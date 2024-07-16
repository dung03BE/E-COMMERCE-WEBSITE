package com.example.shopapp.controller;


import com.example.shopapp.dto.CategoryDTO;
import com.example.shopapp.exception.DataNotFoundException;
import com.example.shopapp.dto.CategoryDTO;
import com.example.shopapp.entity.Category;
import com.example.shopapp.exception.DataNotFoundException;
import com.example.shopapp.form.CreatingCategoryForm;
import com.example.shopapp.service.ICategoryService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;
    @Autowired
    private ModelMapper modelMapper;
    @GetMapping()
    public List<CategoryDTO> getAllCategories()
    {
        List<Category> categories = categoryService.getAllCategories();
        return modelMapper.map(categories, new TypeToken<List<CategoryDTO>>(){}.getType());
    }
    @GetMapping("{id}")
    public CategoryDTO getCategoryById(@PathVariable int id) throws DataNotFoundException, DataNotFoundException {
        Category categoryById = categoryService.getCategoryById(id);
        return modelMapper.map(categoryById,CategoryDTO.class);
    }
    @PostMapping
    public ResponseEntity<Object>createCategory(@RequestBody CreatingCategoryForm form)
    {
         categoryService.createCategory(form);
        return new ResponseEntity<>("Created", HttpStatus.CREATED);
    }
    @PutMapping("{id}")
    public ResponseEntity<String> updateCategory(@PathVariable int id, @RequestBody Category category)
    {
        category.setId(id);
        categoryService.updateCategory(category);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable int id)
    {
        categoryService.deleteCategory(id);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}
