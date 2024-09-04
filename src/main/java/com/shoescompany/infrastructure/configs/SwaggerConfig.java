package com.shoescompany.infrastructure.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.http.HttpHeaders;

@OpenAPIDefinition(
        info = @Info(
                title = "API Shoes Company",
                description = "Esta es la api del proyecto Shoes-Company para el SENA.",
                termsOfService = "www.shoescompany.com/terminos_y_condiciones",
                version = "1.0.0",
                contact = @Contact(
                        name = "Khristian Camilo Escalante Ladino",
                        email = "khristian123_@hotmail.com "
                ),
                license = @License(
                        name = "Licencia gratuita, debido a que un proyecto educativo."
                )
        ),
        servers = {
                @Server(
                        description = "SERVIDOR DE DESARROLLO",
                        url = "http://localhost:8080"
                )
        }
      /*  security = @SecurityRequirement(
                name = "Security Token"
        )*/
)
/*@SecurityScheme(
        name = "Security Token",
        description = "Access Token For My API",
        type = SecuritySchemeType.HTTP,
        paramName = HttpHeaders.AUTHORIZATION,
        in = SecuritySchemeIn.HEADER,
        scheme = "bearer",
        bearerFormat = "JWT"
)*/
public class SwaggerConfig {}
