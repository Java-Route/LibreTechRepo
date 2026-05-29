package com.librotech.catalog.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Riwi HU API",
                version = "1.0",
                description = "Documentacion basica de la API para eventos y venues."
        )
)
public class SwaggerConfig {
}
