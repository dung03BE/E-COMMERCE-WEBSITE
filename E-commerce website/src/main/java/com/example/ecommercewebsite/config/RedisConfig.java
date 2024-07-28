package com.example.ecommercewebsite.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class RedisConfig {
    @Bean
    public LettuceConnectionFactory redisConnection()
    {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName("localhost");
        configuration.setPort(6380);
//        configuration.setDatabase(0);
//        configuration.getUsername("");
//        configuration.getPassword("");
        return new LettuceConnectionFactory(configuration);
    }
    @Bean
    RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnection) {

        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnection);
        return template;
    }

    //khi anh xa truong java ( co truong dl nhu local datetime ) convert json thi -> sang string thì phải chuyển sang ISO
    @Bean
    public ObjectMapper redisObjectMapper()
    {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module= new SimpleModule();
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ISO_DATE_TIME));
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ISO_DATE_TIME));
        objectMapper.registerModule(module);
        return objectMapper;
    }
}
