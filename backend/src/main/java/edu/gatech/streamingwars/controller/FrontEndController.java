package edu.gatech.streamingwars.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class FrontEndController implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/login").setViewName("index");
        registry.addViewController("/forgot-password").setViewName("forgot-password");
        registry.addViewController("/customermain").setViewName("customermain");
        registry.addViewController("/managermain").setViewName("managermain");
        registry.addViewController("/pilotmain").setViewName("pilotmain");
    }

}