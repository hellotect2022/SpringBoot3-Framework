package com.dhhan.customFramework.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@Configuration
//@PropertySource("file:${config.path}/redis.properties")
@PropertySource("classpath:redis.properties")
public class RedisConfig {
    @Bean
    public RedisConnectionFactory lettuceConnectionFactory(Environment env) {
        return standaloneSetup(env);
    }

    private RedisConnectionFactory standaloneSetup(Environment env) {
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        String host = env.getProperty("redis.host", "localhost");
        int port = env.getProperty("redis.port", Integer.class, 6379);
        String password = env.getProperty("redis.password", "");

        redisConfig.setHostName(host);
        redisConfig.setPort(port);
        redisConfig.setPassword(password);

        return new LettuceConnectionFactory(redisConfig);
    }
}
