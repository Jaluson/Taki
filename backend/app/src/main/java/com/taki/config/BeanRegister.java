package com.taki.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.mail.SimpleMailMessage;

@Configuration
public class BeanRegister {

    @Value("${spring.mail.username}")
    private String projectName;

    /**
     * 创建RedisTemplate实例
     * @param redisConnectionFactory Redis连接工厂
     * @return RedisTemplate实例
     */
    @Bean("redisTemplate")
    RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {

        // 创建RedisTemplate实例
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

        // 设置Redis连接工厂
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        // 创建StringRedisSerializer实例
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        // 设置键序列化器
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);

        // 设置值序列化器
        redisTemplate.setValueSerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(stringRedisSerializer);

        // 返回RedisTemplate实例
        return redisTemplate;
    }

    @Bean
    SimpleMailMessage simpleMailMessage(){
        return new SimpleMailMessage(){{
            setFrom(projectName);
        }};
    }
}
