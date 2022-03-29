package com.vadim.sneakerstore.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"org.springdoc"})
@PropertySource("classpath:openapi.yaml")
class OpenApiConfig implements WebMvcConfigurer {

    @Bean
    public GroupedOpenApi publicUserApi() {
        return GroupedOpenApi.builder()
                .group("")
                .pathsToMatch("/products/**")
                .build();
    }

    @Bean
    public OpenAPI customOpenApi(@Value("${description}")String appDescription,
                                 @Value("${version}")String appVersion) {
        return new OpenAPI().info(new Info().title("SneakerStore API")
                .version(appVersion)
                .description(appDescription)
                .contact(new Contact().name("Vadim")
                        .email("vadimbelaev002@gmail.com")));
    }
}
