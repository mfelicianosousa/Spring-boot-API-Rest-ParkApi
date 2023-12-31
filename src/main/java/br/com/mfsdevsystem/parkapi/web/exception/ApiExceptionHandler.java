package br.com.mfsdevsystem.parkapi.web.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.mfsdevsystem.parkapi.exception.CodigoUniqueViolationException;
import br.com.mfsdevsystem.parkapi.exception.EntityNotFoundException;
import br.com.mfsdevsystem.parkapi.exception.PasswordInvalidException;
import br.com.mfsdevsystem.parkapi.exception.UsernameUniqueViolationException;
import jakarta.servlet.http.HttpServletRequest;


@RestControllerAdvice
public class ApiExceptionHandler {
	
	private static Logger logger = LoggerFactory.getLogger( ApiExceptionHandler.class);
	
	@ExceptionHandler( AccessDeniedException.class)
    public ResponseEntity<ErrorMessage> accessDeniedException(AccessDeniedException ex, HttpServletRequest request) {
		logger.info("Api ExceptionHandler Error : ", ex);
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.FORBIDDEN, ex.getMessage()));
    }
	
	
	@ExceptionHandler(PasswordInvalidException.class)
    public ResponseEntity<ErrorMessage> passwordInvalidException(RuntimeException ex, HttpServletRequest request) {
		logger.info("Api ExceptionHandler Error : ", ex);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, ex.getMessage()));
    }
	
	@ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> entityNotFoundException(RuntimeException ex, HttpServletRequest request) {
		logger.info("Api ExceptionHandler Error : ", ex);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.NOT_FOUND, ex.getMessage()));
    }
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorMessage> methodArgumentNotValidException(
			MethodArgumentNotValidException ex,
			HttpServletRequest request,
			BindingResult result) {
		
		logger.info("Api ExceptionHandler Error : ", ex);
		return ResponseEntity
				.status(HttpStatus.UNPROCESSABLE_ENTITY)
				.contentType(MediaType.APPLICATION_JSON)
				.body(new ErrorMessage(request, HttpStatus.UNPROCESSABLE_ENTITY,
						"Campo(s) inválido(s)", result ));
	}
		
	@ExceptionHandler({ UsernameUniqueViolationException.class, CpfUniqueViolationException.class, CodigoUniqueViolationException.class})
	public ResponseEntity<ErrorMessage> uniqueViolationException(
			RuntimeException ex,
			HttpServletRequest request) {
		
		logger.info("Api ExceptionHandler Error : ", ex);
		return ResponseEntity
				.status(HttpStatus.CONFLICT)
				.contentType(MediaType.APPLICATION_JSON)
				.body(new ErrorMessage(request, HttpStatus.CONFLICT,
						ex.getMessage()));
	}
	
	
	@ExceptionHandler( Exception.class)
	public ResponseEntity<ErrorMessage> internalServerErrorException(	 Exception ex, HttpServletRequest request ) {
		ErrorMessage error = new ErrorMessage (
			 request, HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()
		);
		logger.info("Api InternalServerError {} {}: ", error, ex.getMessage());
		return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.contentType(MediaType.APPLICATION_JSON)
				.body( error );
	}
	

}
