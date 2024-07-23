package com.example.shopapp.entity;

import com.example.shopapp.service.IProductRedisService;
import jakarta.persistence.*;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


@AllArgsConstructor
@NoArgsConstructor
public class ProductListener {
    @Autowired
    private IProductRedisService  productRedisService;
    private static final Logger logger = LoggerFactory.getLogger(ProductListener.class);



    @PrePersist
    public void prePersist(Product product)
    {
        logger.info("prePersist");
    }
    @PostPersist
    public void postPersist(Product product)
    {
        logger.info("postUpdate");
        productRedisService.clear();
    }

    @PreUpdate
    public void preUpdate(Product product)
    {
        logger.info("preUpdate");
    }
    @PostUpdate
    public void postUpdate(Product product)
    {
        logger.info("postUpdate");
        productRedisService.clear();
    }
    @PreRemove
    public void preRemove(Product product)
    {
        logger.info("preRemove");
    }

    @PostRemove
    public void postRemove(Product product)
    {
        logger.info("postRemove");
        productRedisService.clear();
    }
}
