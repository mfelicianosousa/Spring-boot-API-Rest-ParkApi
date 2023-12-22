package br.com.mfsdevsystem.parkapi.jwt;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

	private static Logger logger = LoggerFactory.getLogger( JwtAuthorizationFilter.class );
	
	@Autowired
	private JwtUserDetailsService detailsService;
	
	
	//public JwtAuthorizationFilter(JwtUserDetailsService detailsService) {
	//	this.detailsService = detailsService;
	//}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		   final String token = request.getHeader(JwtUtils.JWT_AUTHORIZATION);

		   if ( token == null || !token.startsWith(JwtUtils.JWT_BEARER)) {
			   logger.info("JWT Token está nulo, vázio ou não iniciado com 'Bearer'. {} ",token);
			   filterChain.doFilter(request, response);
			   return;
		   }
		   if ( !JwtUtils.isTokenValid(token)) {
			   logger.warn("JWT Token está inválido ou expirado.");
			   filterChain.doFilter(request, response);
			   return;
		   }
		   
		   String username = JwtUtils.getUsernameFromToken(token);
		   
		   toAuthentication(request, username );
		   
		   filterChain.doFilter( request, response );
		   
	}

	private void toAuthentication( HttpServletRequest request, String username ) {
		
		UserDetails userDetails = detailsService.loadUserByUsername( username );
		
		UsernamePasswordAuthenticationToken authenticationToken = UsernamePasswordAuthenticationToken
				.authenticated( userDetails, null, userDetails.getAuthorities());
		
		authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails( request ));
		
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			
	}
	
}
