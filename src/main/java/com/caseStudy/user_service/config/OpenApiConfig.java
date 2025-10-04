package com.caseStudy.user_service.config;


import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.ExternalDocumentation;


@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI userServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("User Service API")
                        .description("Case study for User Service's Endpoints")
                        .version("v1"))
                .externalDocs(new ExternalDocumentation()
                        .description("API Docs")
                        .url("http://localhost:8080/v3/api-docs"));
    }
}
