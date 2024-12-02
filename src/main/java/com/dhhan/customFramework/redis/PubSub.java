package com.dhhan.customFramework.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnection;
import org.springframework.stereotype.Component;

@Component
public class PubSub {
    @Autowired
    private RedisConnectionFactory lettuceConnectionFactory;

    public long publish(String channelName, String message) {
        try(LettuceConnection conn = (LettuceConnection) lettuceConnectionFactory.getConnection()) {
            return conn.publish(channelName.getBytes(), message.getBytes());
        }
        catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

}
