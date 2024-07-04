package com.example.shopapp.service;



import com.example.shopapp.Exception.DataNotFoundException;
import com.example.shopapp.entity.Category;
import com.example.shopapp.form.CreatingCategoryForm;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ICategoryService {

    List<Category> getAllCategories();

    Category getCategoryById(int id) throws DataNotFoundException;


    void createCategory(CreatingCategoryForm form);

    void updateCategory(Category category);

    void deleteCategory(int id);
}
