package br.com.mfsdevsystem.parkapi;

import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;

import br.com.mfsdevsystem.parkapi.jwt.JwtToken;
import br.com.mfsdevsystem.parkapi.web.controller.AuthenticationController;
import br.com.mfsdevsystem.parkapi.web.dto.UsuarioLoginDto;

public class JwtAuthentication {

	private static Logger logger = LoggerFactory.getLogger( AuthenticationController.class);
	
    public static Consumer<HttpHeaders> getHeaderAuthorization(WebTestClient client, String username, String password) {
        String token = client
                .post()
                .uri("/api/v1/auth")
                .bodyValue( new UsuarioLoginDto( username, password))
                .exchange()
                .expectStatus().isOk()
                .expectBody( JwtToken.class )
                .returnResult().getResponseBody().getToken();
        logger.info("username "+username+ " password"+password);
        logger.info("Token "+token );
        return headers -> headers.add( HttpHeaders.AUTHORIZATION, "Bearer " + token);
    }
}

