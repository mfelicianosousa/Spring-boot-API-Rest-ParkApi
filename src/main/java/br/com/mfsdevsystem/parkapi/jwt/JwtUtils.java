package br.com.mfsdevsystem.parkapi.jwt;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;


public class JwtUtils {

	private static Logger logger = LoggerFactory.getLogger( JwtUtils.class);
	
	public static final String JWT_BEARER = "Bearer ";
	
	public static final String JWT_AUTHORIZATION = "Authorization";
	
	public static final String SECRET_KEY = "0123456789-9876543210-1234567890";
	
	public static final Long EXPIRE_DAYS  = Long.valueOf(0);
	
	public static final Long EXPIRE_HOURS  = Long.valueOf(0);
	
	public static final Long EXPIRE_MINUTES = Long.valueOf(2);
	
	private JwtUtils() {
		
	}
	
	/* io.jsonwebtoken - 0.11.5
	private static Key generateKey() {
		return Keys.hmacShaKeyFor( SECRET_KEY.getBytes(StandardCharsets.UTF_8));
	}
	*/
	private static SecretKey generateKey() {
		return Keys.hmacShaKeyFor( SECRET_KEY.getBytes(StandardCharsets.UTF_8));
		}

	
	private static Date toExpireDate(Date start) {
		LocalDateTime dateTime = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		LocalDateTime end = dateTime.plusDays(EXPIRE_DAYS).plusHours(EXPIRE_HOURS).plusMinutes(EXPIRE_MINUTES);
		return Date.from( end.atZone( ZoneId.systemDefault()).toInstant());
		
	}
	
	/* io.jsonwebtoken - 0.11.5
	public static JwtToken createToken(String username, String role) {
		Date  issuedAt = new Date();
		Date limit = toExpireDate( issuedAt );
		
		String token = Jwts.builder()
				.setHeaderParam("typ","JWT")
				.setSubject(username)
				.setIssuedAt(issuedAt)
				.setExpiration(limit)
				.signWith(generateKey(),SignatureAlgorithm.HS256)
				.claim("role","role")
				.compact();
		
		return new JwtToken(token);
	}
	*/
	
	public static JwtToken createToken(String username, String role) {
		Date issuedAt = new Date();
		Date limit = toExpireDate(issuedAt);
		String token = Jwts.builder()
		.header().add("typ", "JWT")
		.and()
		.subject(username)
		.issuedAt(issuedAt)
		.expiration(limit)
		.signWith(generateKey())
		.claim("role", role)
		.compact();
		return new JwtToken(token);
		}

	/* io.jsonwebtoken - 0.11.5
	 private static Claims getClaimsFromToken(String token) {
		 
		 try {
			return Jwts.parserBuilder()
					.setSigningKey(generateKey())
					.build()
					.parseClaimsJws( refactorToken( token )).getBody();		
		} catch (JwtException ex) {
			
			logger.error(String.format("Token invalido %s",  ex.getMessage()));
			
		}
		 
		return null;
		 
	 }
	 */
	
	private static Claims getClaimsFromToken(String token) {
		try {
			return Jwts.parser()
			.verifyWith(generateKey())
			.build()
			.parseSignedClaims(refactorToken(token)).getPayload();
			
		} catch (JwtException ex) {
				logger.error( String.format( "Token invalido %s", ex.getMessage() ));
			}
			return null;
	}

	 
	 private static String refactorToken(String token) {
		 if (token.contains(JWT_BEARER)) {
			 
			 return token.substring(JWT_BEARER.length());
		 }
		 return token;
		 
	 }
	 
	 public static String getUsernameFromToken(String token) {
		 
		 return getClaimsFromToken(token).getSubject();
		 
	 }
	 
	 /* io.jsonwebtoken - 0.11.5
	 public static boolean isTokenValid(String token) {
		 try {
		 Jwts.parserBuilder()
		 .setSigningKey(generateKey())
		 .build()
		 .parseClaimsJws(refactorToken(token));
		 return true;
		 } catch (JwtException ex) {
		 log.error(String.format("Token invalido %s", ex.getMessage()));
		 }
		 return false;
		 }
     */
	 
	 public static boolean isTokenValid(String token) {
		 try {
			 Jwts.parser()
			          .verifyWith( generateKey() )
			          .build()
			          .parseSignedClaims( refactorToken( token ) );
			 
			 return true ;
			
		} catch (JwtException ex) {
			logger.error(String.format("Token inválido %s", ex.getMessage()));
		}
		 return false;
	 }
	 
}
