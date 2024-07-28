package com.example.ecommercewebsite.service;

import com.example.ecommercewebsite.dto.ProductDTO;
import com.example.ecommercewebsite.form.ProductFilterForm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
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

    private Gson gson = new Gson();
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
        String dataJson = gson.toJson(json);
        List<ProductDTO> productDTOS =
                dataJson!=null ?
                        redisObjectMapper.readValue(dataJson, new TypeReference<List<ProductDTO>>() {})
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
