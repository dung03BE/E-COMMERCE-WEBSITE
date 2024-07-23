package com.example.shopapp.service;

import com.example.shopapp.dto.ProductDTO;
import com.example.shopapp.form.ProductFilterForm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductRedisService implements IProductRedisService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper redisObjectMapper;
    private String getKeyFrom(Pageable pageable)
    {
        int page = pageable.getPageNumber();
        String key = String.format("all_product:%d",page);
        return key;
    }
    @Override
    public List<ProductDTO> getAllProducts(Pageable pageable, ProductFilterForm form) throws JsonProcessingException {
        String key =this.getKeyFrom(pageable);
        String json = (String) redisTemplate.opsForValue().get(key);
        List<ProductDTO> productDTOS =
                json!=null ?
                        redisObjectMapper.readValue(json, new TypeReference<List<ProductDTO>>() {})
                        :null;
        return productDTOS;
    }

    @Override
    public void saveAllProducts(List<ProductDTO> productDTOS, Pageable pageable) throws JsonProcessingException {
        String key = this.getKeyFrom(pageable);
        String json=redisObjectMapper.writeValueAsString(productDTOS);
        redisTemplate.opsForValue().set(key,json);
    }

    @Override
    public void clear() {
        redisTemplate.getConnectionFactory().getConnection().flushAll();
    }


}
