package es.cursojava.springbootrol.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI apiInfo() {
        final String schemeName = "basicAuth";

        return new OpenAPI()
            .info(new Info()
                .title("Juego de Rol - API")
                .description("CRUD básico para pruebas con Postman y documentación Swagger")
                .version("1.0.0")
                .contact(new Contact()
                    .name("Equipo Juego de Rol")
                    .email("sin-email@demo.local"))
                .license(new License()
                    .name("Uso académico")
                    .url("https://example.com")))
            .addServersItem(new Server().url("http://localhost:8085"))
            // Botón Authorize (Basic Auth)
            .components(new Components().addSecuritySchemes(
                schemeName,
                new SecurityScheme()
                    .name(schemeName)
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("basic")
            ))
            // Aplica Basic Auth por defecto a todo
            .addSecurityItem(new SecurityRequirement().addList(schemeName));
    }
}
