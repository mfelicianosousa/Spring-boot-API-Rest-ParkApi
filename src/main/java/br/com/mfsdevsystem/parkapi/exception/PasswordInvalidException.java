package br.com.mfsdevsystem.parkapi.exception;

@SuppressWarnings("serial")
public class PasswordInvalidException extends RuntimeException{

	public PasswordInvalidException(String message) {
		super(message);
	}
}
