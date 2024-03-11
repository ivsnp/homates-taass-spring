package com.homates.bacheca;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableRabbit
@SpringBootApplication
@EnableDiscoveryClient
public class BachecaApplication {
    public static void main(String[] args) {
        SpringApplication.run(BachecaApplication.class, args);
    }

}
