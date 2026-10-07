package com.empaques.desa.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguracion implements WebMvcConfigurer {
    @Value("${app.upload.base:uploads}")
    private String uploadBase;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/uploads/bolsas/**")
                .addResourceLocations("file:" + uploadBase + "/bolsas/");

        registry.addResourceHandler("/uploads/gastos/**")
                .addResourceLocations("file:" + uploadBase + "/gastos/");
    }
}
