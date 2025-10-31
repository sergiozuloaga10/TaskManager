package com.hitss.springboot.task_manager.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                    .info(
                            new Info()
                                    .title("Task Manager API")
                                    .version("1.0.0")
                                    .description("API documentation for Task built with Spring Boot. 3.5.7"))
                                    .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                                    .components(new Components()
                                            .addSecuritySchemes(securitySchemeName,
                                                    new SecurityScheme()
                                                            .name(securitySchemeName)
                                                            .type(SecurityScheme.Type.HTTP)
                                                            .scheme("bearer")
                                                            .bearerFormat("JWT")
                                                            .in(SecurityScheme.In.HEADER)));
    }

}
