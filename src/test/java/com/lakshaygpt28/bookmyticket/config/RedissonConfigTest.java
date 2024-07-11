package com.lakshaygpt28.bookmyticket.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class RedissonConfigTest {

    @Autowired
    private RedissonConfig redissonConfig;

    @Test
    void redissonClient_ShouldReturnNonNullClient() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(RedissonConfig.class);
        RedissonClient redissonClient = context.getBean(RedissonClient.class);
        assertNotNull(redissonClient);
    }
}