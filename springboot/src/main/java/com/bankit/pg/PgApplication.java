package com.bankit.pg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class PgApplication {

    /*
     * Step 1.)
     * Sign up and Get Razorpay Credentials: First, sign up for a Razorpay account
     * if you haven't already. Once signed up, you'll get access to your Razorpay
     * key and secret.
     */

    /*
     * Step 2.)
     * Add Razorpay Dependency: In your Spring Boot project, add the Razorpay
     * dependency to your pom.xml if you're using Maven or build.gradle if you're
     * using Gradle.
     */

    /*
     * Step 3.) Set the client id and secret key credintials in
     * application.properties file
     */

    public static void main(String[] args) {
        SpringApplication.run(PgApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*").allowedMethods("*");
            }
        };
    }

}
