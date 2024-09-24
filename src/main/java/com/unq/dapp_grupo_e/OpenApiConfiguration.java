package com.unq.dapp_grupo_e;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI defineOpenAPI() {
        Server server = new Server();
        server.setUrl("http://localhost:8080");
        server.setDescription("Crypto Exchange - Group E");

        Info information = new Info()
            .title("Crypto Exchange API")
            .version("0.1")
            .description("This API exposes endpoints for the exchange of crypto currency between its users");
        return new OpenAPI().info(information).servers(List.of(server));
    }
    
}
