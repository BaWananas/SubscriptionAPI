package com.learnings.learningproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@SpringBootApplication
public class LearningProjectApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(LearningProjectApplication.class);
        app.run(args);
    }
}
