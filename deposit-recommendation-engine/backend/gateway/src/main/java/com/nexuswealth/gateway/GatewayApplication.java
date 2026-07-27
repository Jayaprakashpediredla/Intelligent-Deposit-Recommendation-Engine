package com.nexuswealth.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("customer-service", r -> r
                .path("/api/v1/customers/**")
                .uri("http://customer-service:8001"))
            .route("transaction-service", r -> r
                .path("/api/v1/transactions/**")
                .uri("http://transaction-service:8002"))
            .route("recommendation-service", r -> r
                .path("/api/v1/recommendations/**")
                .uri("http://recommendation-service:8003"))
            .build();
    }

    @GetMapping({"/", "/health"})
    public String health() {
        return "Gateway is running";
    }
}

