package com.learnings.learningproject;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@SpringBootApplication
public class LearningProjectApplication {

    @Bean
    WebMvcConfigurer configurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/site/**")
                        .addResourceLocations("classpath:/site/")
                        .resourceChain(true)
                        .addResolver(new PathResourceResolver() {
                            @Override
                            protected Resource getResource(@NotNull String resourcePath, Resource location) throws IOException {
                                Resource requestedResource = location.createRelative(resourcePath);

                                return requestedResource.exists() && requestedResource.isReadable() ? requestedResource
                                        : new ClassPathResource("/site/index.html");
                            }
                        });
            }
        };
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(LearningProjectApplication.class);
        app.run(args);
        try {
            initFirebaseSDK();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void initFirebaseSDK() throws IOException {
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(new FileInputStream(new File("C:\\Users\\beerna\\IdeaProjects\\learningproject\\src\\main\\resources\\service-account-firebase.json"))))
                //.setDatabaseUrl("https://<DATABASE_NAME>.firebaseio.com/")
                .build();

        FirebaseApp.initializeApp(options);
    }
}
