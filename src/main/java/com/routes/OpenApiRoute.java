//package com.routes;
//
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class OpenApiRoute {
//
//    @Bean
//    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route(r -> r.path("/openApi/**")
//                        .filters(f ->
//                            f.stripPrefix(1).addRequestHeader("requestType", "openApi")
//                            )
//                        .uri("http://openApi:9100"))
//                .route(r -> r.host("*.lehoyan.com")
//                        .filters(f -> f.hystrix(config -> config.setName("hystrixConfig").setFallbackUri("forward:/fallback")))
//                        .uri("http://openApi:9100"))
//                .build();
//    }
//}
