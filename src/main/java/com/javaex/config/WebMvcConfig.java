package com.javaex.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // 주소는 무조건 api로 시작
        .allowedMethods("GET", "POST", "PUT", "DELETE") // 네가지 경우 허락
        .allowedOrigins("http://localhost:8080") //vscode에서 주소 허락
        .allowedHeaders("*") // 모든 요청해더
        .exposedHeaders("Authorization")// 노출시킬 헤더
        .allowCredentials(true); // 쿠키허용
        ;
    }
}
