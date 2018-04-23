package com.meraj.microservices.monitordashboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableHystrixDashboard // http://localhost:7979/hystrix
public class MonitordashboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(MonitordashboardApplication.class, args);
    }
}
