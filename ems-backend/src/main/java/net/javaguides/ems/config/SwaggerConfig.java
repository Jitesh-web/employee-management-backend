package net.javaguides.ems.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI employeeManagementOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("Employee Management System API")
                        .description("REST APIs for Employee Management System")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Jitesh Narode")
                                .email("jiteshnarode@example.com")))
                .externalDocs(new ExternalDocumentation()
                        .description("Project Documentation")
                        .url("https://github.com/Jitesh-web"));
    }
}