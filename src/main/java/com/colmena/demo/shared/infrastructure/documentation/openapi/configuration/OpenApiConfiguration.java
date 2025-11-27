package com.colmena.demo.shared.infrastructure.documentation.openapi.configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {
    @Value("Backend Finanzas")
    String applicationName;

    @Value("@project.description@")
    String applicationDescription;

    @Value("@project.version@")
    String applicationVersion;

    @Bean
    public OpenAPI learninfPlatformOpenApi() {

        //General configuration
        var openApi = new OpenAPI();
        openApi
                .info(new Info()
                        .title(this.applicationName)
                        .description(this.applicationDescription)
                        .version(this.applicationVersion)
                        .license(new License().name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")))
                .externalDocs(new ExternalDocumentation()
                        .description("Grupo Colmena")
                        .url("https://acme-learning-center-platform.wiki.github.io/docs"));

        return openApi;
    }
}
