package br.com.mfsdevsystem.parkapi.exception;

@SuppressWarnings("serial")
public class EntityNotFoundException extends RuntimeException{

	public EntityNotFoundException(String message) {
		super(message);
	}
}
