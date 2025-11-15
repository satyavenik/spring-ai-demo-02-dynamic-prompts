package com.example.springai.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("spring-ai-demo-02-dynamic-prompts API")
                .version("0.0.1")
                .description("Spring AI API with dynamic prompt templates")
                .contact(new Contact().name("satyavenik"))
                .license(new License().name("Apache 2.0").url("http://springdoc.org"))
            );
    }
}

