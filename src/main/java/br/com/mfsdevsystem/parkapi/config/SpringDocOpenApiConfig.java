package br.com.mfsdevsystem.parkapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SpringDocOpenApiConfig {

	@Bean
	public OpenAPI openAPI() {
		
		return new OpenAPI()
				.components(new Components().addSecuritySchemes("security", securityScheme()))
				.info(
						new Info()
						.title("REST API - Spring Park")
						.description("API para Gestão de Estacionamento de veiculos")
						.version("v1")
						.license(new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENCE-2.0"))
						.contact(new Contact().name("Marcelino Feliciano de Sousa").email("marcelino.feliciano@outlook.com"))
						
				);
	}
	
	private SecurityScheme securityScheme() {
		return new SecurityScheme()
				.description("Insira um bearer token valido para prosseguir")
				.type(SecurityScheme.Type.HTTP)
				.in(SecurityScheme.In.HEADER)
				.scheme("bearer")
				.bearerFormat("JWT")
				.name("security");		
	}
}
