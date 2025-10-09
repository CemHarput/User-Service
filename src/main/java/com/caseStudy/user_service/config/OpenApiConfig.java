package com.caseStudy.user_service.config;


import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springdoc.core.customizers.OpenApiCustomizer;
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
                        .url("http://localhost:8081/v3/api-docs"));
    }
    @Bean
    public OpenApiCustomizer xUserIdOnlyForWriteOps() {
        return openApi -> {
            Parameter xUserId = new Parameter()
                    .in(ParameterIn.HEADER.toString())
                    .name("X-Acting-User")
                    .description("Acting org id (UUID) – for auditing")
                    .required(true)
                    .schema(new StringSchema().format("uuid")
                            .example("2ac9dca0-b2f4-45c6-bb6c-649912d756f8"));

            openApi.getPaths().values().forEach(pathItem ->
                    pathItem.readOperationsMap().forEach((method, op) -> {
                        if (method != PathItem.HttpMethod.GET
                                && method != PathItem.HttpMethod.OPTIONS
                                && method != PathItem.HttpMethod.HEAD) {
                            op.addParametersItem(xUserId);
                        }
                    })
            );
        };
    }
}
