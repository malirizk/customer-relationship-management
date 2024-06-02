package com.example.calllogservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EntityScan("org.example.common.model")
@EnableDiscoveryClient
@SpringBootApplication
public class CallLogServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CallLogServiceApplication.class, args);
    }

}
