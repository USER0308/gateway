//package com.swagger;
//
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.beans.factory.annotation.Value;
////import org.springframework.cloud.gateway.route.RouteLocator;
////import org.springframework.stereotype.Component;
////import springfox.documentation.swagger.web.SwaggerResource;
////import springfox.documentation.swagger.web.SwaggerResourcesProvider;
////
////import java.util.ArrayList;
////import java.util.HashSet;
////import java.util.List;
////import java.util.Set;
////
////@Component
////public class SwaggerResourceProvider implements SwaggerResourcesProvider {
////
////    /**
////     * swagger2默认的url后缀
////     */
////    private static final String SWAGGER2URL = "/v2/api-docs";
////
////    /**
////     * 网关路由
////     */
////    private final RouteLocator routeLocator;
////
////    /**
////     * 网关应用名称
////     */
////    @Value("${spring.application.name}")
////    private String self;
////
////    @Autowired
////    public SwaggerResourceProvider(RouteLocator routeLocator) {
////        this.routeLocator = routeLocator;
////    }
////
////    @Override
////    public List<SwaggerResource> get() {
////        List<SwaggerResource> resources = new ArrayList<>();
////        List<String> routeHosts = new ArrayList<>();
////        // 获取所有可用的host：serviceId
////        routeLocator.getRoutes().filter(route -> route.getUri().getHost() != null)
////                .filter(route -> !self.equals(route.getUri().getHost()))
////                .subscribe(route -> routeHosts.add(route.getUri().getHost()));
////
////        // 记录已经添加过的server，存在同一个应用注册了多个服务在eureka上
////        Set<String> dealed = new HashSet<>();
////        routeHosts.forEach(instance -> {
////            // 拼接url
////            String url = "/" + instance.toLowerCase() + SWAGGER2URL;
////            if (!dealed.contains(url)) {
////                dealed.add(url);
////                SwaggerResource swaggerResource = new SwaggerResource();
////                swaggerResource.setUrl(url);
////                swaggerResource.setName(instance);
////                resources.add(swaggerResource);
////            }
////        });
////        return resources;
////    }
////}
//
//import lombok.AllArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.gateway.config.GatewayProperties;
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.context.annotation.Primary;
//import org.springframework.stereotype.Component;
//import springfox.documentation.swagger.web.SwaggerResource;
//import springfox.documentation.swagger.web.SwaggerResourcesProvider;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//@Primary
//@AllArgsConstructor
//public class SwaggerResourceProvider implements SwaggerResourcesProvider {
//
//    public static final String API_URI = "/v2/api-docs";
//    @Autowired
//    private final RouteLocator routeLocator;
//    @Autowired
//    private final GatewayProperties gatewayProperties;
//
//
//    @Override
//    public List<SwaggerResource> get() {
//        List<SwaggerResource> resources = new ArrayList<>();
//        List<String> routes = new ArrayList<>();
//        //取出gateway的route
//        routeLocator.getRoutes().subscribe(route -> routes.add(route.getId()));
//        //结合配置的route-路径(Path)，和route过滤，只获取有效的route节点
//        gatewayProperties.getRoutes().stream().filter(routeDefinition -> routes.contains(routeDefinition.getId()))
//                .forEach(routeDefinition -> routeDefinition.getPredicates().stream()
//                        .filter(predicateDefinition -> ("Path").equalsIgnoreCase(predicateDefinition.getName()))
//                        .forEach(predicateDefinition -> resources.add(swaggerResource(routeDefinition.getId(),
//                                "/"+routeDefinition.getId()+API_URI ))));
//        return resources;
//    }
//
//    private SwaggerResource swaggerResource(String name, String location) {
//        SwaggerResource swaggerResource = new SwaggerResource();
//        swaggerResource.setName(name);
//        swaggerResource.setLocation(location);
//        swaggerResource.setSwaggerVersion("2.0");
//        return swaggerResource;
//
//    }
//
//}