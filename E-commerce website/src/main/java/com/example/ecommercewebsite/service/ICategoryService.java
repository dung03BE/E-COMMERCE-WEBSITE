package com.example.ecommercewebsite.service;



import com.example.ecommercewebsite.exception.DataNotFoundException;
import com.example.ecommercewebsite.entity.Category;
import com.example.ecommercewebsite.form.CreatingCategoryForm;

import java.util.List;

public interface ICategoryService {

    List<Category> getAllCategories();

    Category getCategoryById(int id) throws DataNotFoundException;


    void createCategory(CreatingCategoryForm form);

    void updateCategory(Category category);

    void deleteCategory(int id);
}
