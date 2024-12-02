package com.dhhan.customFramework;

import com.dhhan.customFramework.redis.PubSub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CustomFrameworkApplication {
	@Autowired
	PubSub pubSub;

	public static void main(String[] args) {
		SpringApplication.run(CustomFrameworkApplication.class, args);
	}

}
