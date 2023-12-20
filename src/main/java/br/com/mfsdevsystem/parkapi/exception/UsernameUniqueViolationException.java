package br.com.mfsdevsystem.parkapi.exception;

@SuppressWarnings("serial")
public class UsernameUniqueViolationException extends RuntimeException {

	public UsernameUniqueViolationException(String message) {
		super(message);
		
	}
}