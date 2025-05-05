package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(@NonNull ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("classpath:/static/images/")
                // Vô hiệu cache hoàn toàn
                .setCacheControl(CacheControl.noCache());
//        registry.addResourceHandler("/admin/**")
//                .addResourceLocations("classpath:/static/admin/")
//                .setCacheControl(CacheControl.noCache());
        // Nếu muốn set thời gian cache ngắn, ví dụ 60 giây, bạn có thể dùng:
        // .setCacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS).cachePublic());
    }
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/admin/**").setViewName("forward:/admin/index.html");
    }
}
