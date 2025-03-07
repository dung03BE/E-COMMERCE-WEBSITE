package com.example.ecommercewebsite.service;


import com.example.ecommercewebsite.exception.DataNotFoundException;
import com.example.ecommercewebsite.entity.Category;
import com.example.ecommercewebsite.form.CreatingCategoryForm;
import com.example.ecommercewebsite.repository.ICategoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

@Service

@RequiredArgsConstructor
public class CategoryService implements ICategoryService{
    @Autowired
    private ICategoryRepository categoryRepository;
    @Autowired
    ModelMapper modelMapper;
//    @Cacheable("categoryHome")
    @Autowired
    RedisTemplate redisTemplate;

    private Gson gson = new Gson();

    private final RedisTemplate<String, Object> redisTemplate1;
    private final ObjectMapper redisObjectMapper;
    @Override
    public List<Category> getAllCategories() {
//        System.out.println("CACHE chạy");
        String dataRedis = (String) redisTemplate.opsForValue().get("category");
        List<Category> categoryList;
        if(dataRedis==null)
        {
            System.out.println("Chua co dâta");
            categoryList = categoryRepository.findAll();
            String dataJson =gson.toJson(categoryList);
            redisTemplate.opsForValue().set("category",dataJson);
        }
        else
        {
            Type listType = new TypeToken<List<Category>>(){}.getType();
            categoryList = gson.fromJson(dataRedis,listType);
            System.out.println("CO dâta");
        }

        return categoryList;
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
