package com.leaguewatch.wishlistservice.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().addSecurityItem(new SecurityRequirement().addList("Bearer Authorization")).components(new Components().addSecuritySchemes("Bearer Authorization", createAPIkeyScheme())).info(new Info().title("Wishlist Service RESTFul API").description("Rest APIs").version("1.0").contact(new Contact().name("Vaibhav Tomar").email("tomar.vaibhav.contact@gmail.com").url("https://github.com/VaibhavT99")).license(new License().name("Apache License 2.0")));
    }


    private SecurityScheme createAPIkeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP).bearerFormat("JWT").scheme("Bearer");
    }

}
