package br.com.mfsdevsystem.parkapi.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import br.com.mfsdevsystem.parkapi.web.exception.ErrorMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class AuthenticationController {

	private static Logger logger = LoggerFactory.getLogger( AuthenticationController.class);
	
//	@Autowired
	private final JwtUserDetailsService detailsService;
//	@Autowired
	private final AuthenticationManager authenticationManager;
	
	
	public AuthenticationController(JwtUserDetailsService detailsService, AuthenticationManager authenticationManager) {
		this.detailsService = detailsService;
		this.authenticationManager = authenticationManager;
	}
	
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
