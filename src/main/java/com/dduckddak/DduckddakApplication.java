package com.dduckddak;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(servers = {
		@Server(url = "https://api.gadduck.info", description = "Default Server URL")
})
public class DduckddakApplication {

	public static void main(String[] args) {
		SpringApplication.run(DduckddakApplication.class, args);
	}

}
