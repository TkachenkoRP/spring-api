package com.example.springapi.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfiguration {

    @Value("${spring.openApi.url}")
    private String apiUrl;

    @Bean
    public OpenAPI openApiDescription() {
        Server localhostServer = new Server();
        localhostServer.setUrl(apiUrl);
        localhostServer.setDescription("Local env");

        Contact contact = new Contact();
        contact.setName("Rodion");
        contact.setEmail("someemail@example");

        License mitLicense = new License().name("GNU AGPLv3")
                .url("https://choosealicenese.com/licesnse/agpl-3.0/");

        Info info = new Info()
                .title("My App API")
                .version("1.0")
                .contact(contact)
                .description("API for My App")
                .termsOfService("http://some.terms.url")
                .license(mitLicense);

        return new OpenAPI().info(info).servers(List.of(localhostServer));
    }

}
