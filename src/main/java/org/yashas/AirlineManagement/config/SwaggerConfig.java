package org.yashas.AirlineManagement.config;

import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.*;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "AIRLINE MANAGEMENT API",
                version = "Version 1.0",
                description = "Airline Management API developed with Spring Boot.",
                contact = @Contact(
                        name = "Yashas C Raju",
                        email = "yashascraju1@gmail.com",
                        url = "https://www.linkedin.com/in/yashas-c-853b7b336/"
                )
        )
)
public class SwaggerConfig {

}
