package br.com.mfsdevsystem.parkapi.web.exception;

public class CpfUniqueViolationException extends RuntimeException {
	
	public CpfUniqueViolationException(String message) {
		super(message);
	}

}
