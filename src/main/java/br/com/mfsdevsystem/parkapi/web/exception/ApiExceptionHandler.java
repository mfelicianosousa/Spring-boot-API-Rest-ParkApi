package br.com.mfsdevsystem.parkapi.web.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.mfsdevsystem.parkapi.exception.EntityNotFoundException;
import br.com.mfsdevsystem.parkapi.exception.UsernameUniqueViolationException;
import jakarta.servlet.http.HttpServletRequest;


@RestControllerAdvice
public class ApiExceptionHandler {
	
	private static Logger logger = LoggerFactory.getLogger( ApiExceptionHandler.class);
	
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
		
	@ExceptionHandler(UsernameUniqueViolationException.class)
	public ResponseEntity<ErrorMessage> UniqueViolationException(
			RuntimeException ex,
			HttpServletRequest request) {
		
		logger.info("Api ExceptionHandler Error : ", ex);
		return ResponseEntity
				.status(HttpStatus.CONFLICT)
				.contentType(MediaType.APPLICATION_JSON)
				.body(new ErrorMessage(request, HttpStatus.CONFLICT,
						ex.getMessage()));
	}

}
