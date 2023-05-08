//package com.gocarshare.authserver.Configurations;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//
//
//@Configuration
//@EnableWebMvc
//public class MyCorsConfiguration implements WebMvcConfigurer {
//
//  @Override
//  public void addCorsMappings(CorsRegistry registry) {
//    registry.addMapping( "/**")
//      .allowedOrigins("http://localhost:4200", "/**", "*")
//      .allowedMethods("GET", "POST", "PUT", "DELETE")
//      .allowedHeaders("*")
//      .maxAge(3600);
//  }
//}
