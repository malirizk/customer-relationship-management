package org.example.callreportservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EntityScan("org.example.common.model")
@EnableDiscoveryClient
@SpringBootApplication
public class CallReportServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CallReportServiceApplication.class, args);
    }
}