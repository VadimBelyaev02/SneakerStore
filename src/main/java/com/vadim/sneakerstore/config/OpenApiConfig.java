package com.vadim.sneakerstore.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
//import org.springframework.hateoas.server.LinkRelationProvider;
//import org.springframework.hateoas.server.core.AnnotationLinkRelationProvider;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"org.springdoc"})
//@Import({org.springdoc.core.SpringDocConfiguration.class,
//        org.springdoc.webmvc.core.SpringDocWebMvcConfiguration.class,
//        org.springdoc.webmvc.ui.SwaggerConfig.class,
//        org.springdoc.core.SwaggerUiConfigProperties.class,
//        org.springdoc.core.SwaggerUiOAuthProperties.class,
//        org.springdoc.core.SpringDocConfigProperties.class,
//        org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration.class})
@PropertySource("classpath:openapi.yaml")
class OpenApiConfig implements WebMvcConfigurer {

//    @Bean
//    public LinkRelationProvider linkRelationProvider() {
//        return new AnnotationLinkRelationProvider();
//    }

    @Bean
    public GroupedOpenApi publicUserApi() {
        return GroupedOpenApi.builder()
                .group("")
                .pathsToMatch("/customers/**")
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
