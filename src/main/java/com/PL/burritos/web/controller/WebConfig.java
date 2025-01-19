package com.PL.burritos.web.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// Can use this instead of just a home controller that only forwards the request to the home view
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    // Registers / as view controller that forwards to home.html
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("home");
    }
}

