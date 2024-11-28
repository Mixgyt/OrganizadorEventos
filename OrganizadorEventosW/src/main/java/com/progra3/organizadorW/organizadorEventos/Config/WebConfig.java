package com.progra3.organizadorW.organizadorEventos.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration  // Indica que esta clase es una configuración de Spring
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/auth/login").setViewName("login");
        registry.addViewController("/auth/register").setViewName("register");
        registry.addViewController("/usuario/home").setViewName("index");
    }
}