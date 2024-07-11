package com.lakshaygpt28.bookmyticket.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI defineOpenApi() {
        Server server = new Server();
        server.setUrl("http://localhost:8080");
        server.setDescription("Development");

        Contact myContact = new Contact();
        myContact.setName("Lakshay Gupta");
        myContact.setEmail("lakshaygpt28@gmail.com");

        Info information = new Info()
                .title("BookMyTicket API")
                .version("1.0")
                .description("API documentation for the BookMyTicket application")
                .contact(myContact);
        return new OpenAPI().info(information).servers(List.of(server));
    }
}