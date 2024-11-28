package com.progra3.organizadorW.organizadorEventos.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration  // Indica que esta clase es una configuración de Spring
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // Mapea la URL "/clientes/login" a la vista "login"
        registry.addViewController("/auth/login").setViewName("login");
        // Mapea la URL "/clientes/register" a la vista "register"
        registry.addViewController("/auth/register").setViewName("register");
        // Mapea la URL "/clientes/home" a la vista "home"
        //registry.addViewController("/clientes/home").setViewName("home");
    }
}