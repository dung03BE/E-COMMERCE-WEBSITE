package com.example.shopapp.service;


import com.example.shopapp.Exception.DataNotFoundException;
import com.example.shopapp.entity.Category;
import com.example.shopapp.form.CreatingCategoryForm;
import com.example.shopapp.repository.ICategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class CategoryService implements ICategoryService{
    @Autowired
    private ICategoryRepository categoryRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(int id) throws DataNotFoundException {
        return categoryRepository.findById(id)
                .orElseThrow(()-> new DataNotFoundException("Cannot find category with id   ="+id));
    }

    @Override
    public void createCategory(CreatingCategoryForm form) {
        Category category =modelMapper.map(form,Category.class);
        categoryRepository.save(category);
    }

    @Override
    public void updateCategory(Category category) {

        categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(int id) {
        categoryRepository.deleteById(id);
    }

}
