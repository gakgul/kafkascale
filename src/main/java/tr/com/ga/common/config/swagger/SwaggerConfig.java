package tr.com.ga.common.config.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Configuration
@Profile("dev")
public class SwaggerConfig {

    public static final String BEARER_FORMAT = "JWT";
    public static final String SECURITY_NAME = "kafkasample_jwt";
    public static final String SCHEMA_TYPE = "bearer";
/*
    @Bean
    public OpenAPI butunlesiOpenApi() {

        return new OpenAPI()
                .info(new Info().title("Kafka Sample API")
                        .description("Kafka Sample Api Documentation")
                        .version("v0.0.1")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://localhost:8080")))
                .components(new Components()
                        .addSecuritySchemes(SECURITY_NAME, securityScheme()))
                .addSecurityItem(new SecurityRequirement()
                        .addList(SECURITY_NAME, Arrays.asList("read", "write")));
    }

    public SecurityScheme securityScheme() {
        return new SecurityScheme()
                .name(SECURITY_NAME)
                .scheme(SCHEMA_TYPE)
                .bearerFormat(BEARER_FORMAT)
                .type(SecurityScheme.Type.HTTP)
                .in(SecurityScheme.In.HEADER);
    }
*/

}