package br.com.mfsdevsystem.parkapi.web.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import br.com.mfsdevsystem.parkapi.jwt.JwtAuthorizationFilter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private static Logger logger = LoggerFactory.getLogger( JwtAuthorizationFilter.class );
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		
		logger.info("Http status 401 {} ",authException.getMessage());
		response.setHeader("www-authenticator", "Bearer realm='/api/v1/auth'");
		response.sendError(401);
		
	}
	

}
