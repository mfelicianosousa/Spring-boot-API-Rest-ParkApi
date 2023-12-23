package br.com.mfsdevsystem.parkapi.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mfsdevsystem.parkapi.jwt.JwtToken;
import br.com.mfsdevsystem.parkapi.jwt.JwtUserDetailsService;
import br.com.mfsdevsystem.parkapi.web.dto.UsuarioLoginDto;
import br.com.mfsdevsystem.parkapi.web.dto.UsuarioResponseDto;
import br.com.mfsdevsystem.parkapi.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Tag(name = "Autenticação", description = "Recurso para proceder com  a autenticação na API")
@RestController
@RequestMapping("/api/v1")
public class AuthenticationController {

	private static Logger logger = LoggerFactory.getLogger( AuthenticationController.class);
	

	private final JwtUserDetailsService detailsService;
	private final AuthenticationManager authenticationManager;
	
	
	public AuthenticationController(JwtUserDetailsService detailsService, AuthenticationManager authenticationManager) {
		this.detailsService = detailsService;
		this.authenticationManager = authenticationManager;
	}
	
	@Operation( summary="Autenticar na API", description="Recurso de autenticação na API",
			responses = {
					@ApiResponse( responseCode = "200", description ="Autenticação realizada com sucesso e retorno de um Bearer token",
					    content = @Content(mediaType="application/json",
					    schema = @Schema(implementation = UsuarioResponseDto.class))),
					@ApiResponse( responseCode = "400", description = "Credenciais inválidas",
					    content = @Content(mediaType="application/json",
					    schema = @Schema( implementation = ErrorMessage.class ))),
					@ApiResponse( responseCode = "422", description = "Campos inválido(s)",
					    content = @Content(mediaType="application/json",
					    schema = @Schema( implementation = ErrorMessage.class )))
			}		
	)
	@PostMapping("/auth")
	public ResponseEntity<?> authenticate(@RequestBody @Valid UsuarioLoginDto dto, HttpServletRequest request){
		 logger.info("Processo de authenticaçao pelo login {} ",  dto.getUsername());
		 try {
			 	  UsernamePasswordAuthenticationToken authenticationToken = 
			 			new UsernamePasswordAuthenticationToken( dto.getUsername(),  dto.getPassword());
			 	  authenticationManager.authenticate(authenticationToken) ;
			 	  
			 	  JwtToken token = detailsService.getTokenAuthenticated(dto.getUsername());
			 	  
			 	  return ResponseEntity.ok( token );
			 	
		} catch (AuthenticationException ex) {
			 logger.warn("Bad Credentials from username '{}' ", dto.getUsername());
		}
		 
		 return ResponseEntity
					 .badRequest()
					 .body( new ErrorMessage(request, HttpStatus.BAD_REQUEST, "Credenciais Inválidas"));
	}
	
}
