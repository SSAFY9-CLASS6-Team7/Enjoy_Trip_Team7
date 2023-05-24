package com.ssafy.config;

import com.ssafy.enjoytrip.interceptor.JwtInterceptor;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@MapperScan(basePackages = {"com.ssafy.**.mapper"})
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private String connectPath = "/imagePath/**";
    private String resourcePath = "file:///C:/enjoytrip_image/images/";
    private String profilePath = "/profilePath/**";
    private String profileResource = "file:///C:/enjoytrip_image/profile/";
//    private final JwtInterceptor jwtInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(connectPath)
                .addResourceLocations(resourcePath);
        registry.addResourceHandler(profilePath)
                .addResourceLocations(profileResource);
    }

    /*@Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**"); // 모든 URL에 대해 JwtInterceptor가 실행됩니다.
    }*/
}
