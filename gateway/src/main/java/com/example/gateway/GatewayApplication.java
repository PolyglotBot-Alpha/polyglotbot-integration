package com.example.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/api/python/**")
                        .uri("lb://PYTHON-SERVICE"))
                .route("java_user_route", r -> r.path("/api/user/**")
                        .filters(f -> f.rewritePath("/api/user/(?<segment>.*)", "/user/${segment}"))
                        .uri("http://localhost:8081"))
                .build();
    }
}
