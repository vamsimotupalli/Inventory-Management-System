package com.example.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfiguration {

    @Bean
    public RedisTemplate<Integer,Object> redisTemplate(RedisConnectionFactory factory)
    {
        RedisTemplate<Integer,Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setHashKeySerializer(new GenericToStringSerializer<>(Integer.class));
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }

}
