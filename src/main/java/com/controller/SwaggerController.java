//package com.controller;
//
////import com.swagger.SwaggerResourceProvider;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.http.HttpStatus;
////import org.springframework.http.ResponseEntity;
////import org.springframework.web.bind.annotation.RequestMapping;
////import org.springframework.web.bind.annotation.RestController;
////import springfox.documentation.swagger.web.*;
////
////import java.util.List;
////
////@RestController
////@RequestMapping("/swagger-resources")
////public class SwaggerController {
////    @Autowired
////    private SwaggerResourceProvider swaggerResourceProvider;
////
////    @RequestMapping(value = "/configuration/security")
////    public ResponseEntity<SecurityConfiguration> securityConfiguration() {
////        return new ResponseEntity<>(SecurityConfigurationBuilder.builder().build(), HttpStatus.OK);
////    }
////
////    @RequestMapping(value = "/configuration/ui")
////    public ResponseEntity<UiConfiguration> uiConfiguration() {
////        return new ResponseEntity<>(UiConfigurationBuilder.builder().build(), HttpStatus.OK);
////    }
////
////    @RequestMapping
////    public ResponseEntity<List<SwaggerResource>> swaggerResources() {
////        return new ResponseEntity<>(swaggerResourceProvider.get(), HttpStatus.OK);
////    }
////}
//
//import com.swagger.SwaggerResourceProvider;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import reactor.core.publisher.Mono;
//import springfox.documentation.swagger.web.*;
//
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/swagger-resources")
//public class SwaggerController {
//    @Autowired(required = false)
//    private SecurityConfiguration securityConfiguration;
//    @Autowired(required = false)
//    private UiConfiguration uiConfiguration;
//    private final SwaggerResourceProvider swaggerResource;
//
//    @Autowired
//    public SwaggerController(SwaggerResourceProvider swaggerResource) {
//        this.swaggerResource = swaggerResource;
//    }
//
//
//    @GetMapping("/configuration/security")
//    public Mono<ResponseEntity<SecurityConfiguration>> securityConfiguration() {
//        return Mono.just(new ResponseEntity<>(
//                Optional.ofNullable(securityConfiguration).orElse(SecurityConfigurationBuilder.builder().build()), HttpStatus.OK));
//    }
//
//    @GetMapping("/configuration/ui")
//    public Mono<ResponseEntity<UiConfiguration>> uiConfiguration() {
//        return Mono.just(new ResponseEntity<>(
//                Optional.ofNullable(uiConfiguration).orElse(UiConfigurationBuilder.builder().build()), HttpStatus.OK));
//    }
//
//    @GetMapping("")
//    public Mono<ResponseEntity> swaggerResources() {
//        return Mono.just((new ResponseEntity<>(swaggerResource.get(), HttpStatus.OK)));
//    }
//
//
//}