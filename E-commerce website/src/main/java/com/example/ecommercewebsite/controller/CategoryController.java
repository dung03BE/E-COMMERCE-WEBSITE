package com.example.ecommercewebsite.controller;


import com.example.ecommercewebsite.dto.CategoryDTO;
import com.example.ecommercewebsite.exception.DataNotFoundException;
import com.example.ecommercewebsite.entity.Category;
import com.example.ecommercewebsite.form.CreatingCategoryForm;
import com.example.ecommercewebsite.service.ICategoryService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/categories")
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

    @GetMapping("/{id}")
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
    @CacheEvict(value = "categoryHome",allEntries = true)
    @GetMapping("/clear-cache")
    public String clearCache()
    {
        return "Clear cache success";
    }
}
