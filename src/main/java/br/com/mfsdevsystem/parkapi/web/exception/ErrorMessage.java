package br.com.mfsdevsystem.parkapi.web.exception;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.servlet.http.HttpServletRequest;

@SuppressWarnings("serial")
public class ErrorMessage implements Serializable {
	
	private Instant timestamp;
	private String path;
	private String method;
	private int status;
	private String StatusText;
	private String message;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Map<String, String> errors;
	
	public ErrorMessage() {
		
	}
	
	public ErrorMessage(HttpServletRequest request, HttpStatus status, String message) {
		this.timestamp = Instant.now();
		this.path = request.getRequestURI();
		this.method = request.getMethod();
		this.status = status.value();
		this.StatusText = status.getReasonPhrase();
		this.message = message;
		
	}
	
	public ErrorMessage(HttpServletRequest request, HttpStatus status, String message, BindingResult result) {
		this.timestamp = Instant.now();
		this.path = request.getRequestURI();
		this.method = request.getMethod();
		this.status = status.value();
		this.StatusText = status.getReasonPhrase();
		this.message = message;
		addErrors(result);
		
	}

	private void addErrors(BindingResult result) {
		this.errors = new HashMap<>();
		for (FieldError fieldError: result.getFieldErrors()) {
			this.errors.put(fieldError.getField(), fieldError.getDefaultMessage());
			
		}
		
	}

	public Instant getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Instant timestamp) {
		this.timestamp = timestamp;
	}
	
	
	public String getPath() {
		return path;
	}

	public String getMethod() {
		return method;
	}

	public int getStatus() {
		return status;
	}

	public String getStatusText() {
		return StatusText;
	}

	public String getMessage() {
		return message;
	}

	public Map<String, String> getErrors() {
		return errors;
	}

	@Override
	public String toString() {
		return "ErrorMessage [timestamp=" + timestamp + ", path=" + path + ", method=" + method + ", status=" + status
				+ ", StatusText=" + StatusText + ", message=" + message + ", errors=" + errors + "]";
	}

}
