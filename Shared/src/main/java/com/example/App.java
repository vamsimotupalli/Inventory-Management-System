package com.example;

import org.springframework.ai.vectorstore.redis.autoconfigure.RedisVectorStoreAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication(exclude = RedisVectorStoreAutoConfiguration.class)
public class App
{
    public static void main( String[] args )
    {
        SpringApplication.run(App.class, args);
    }
}