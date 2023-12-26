package br.com.mfsdevsystem.parkapi.exception;

@SuppressWarnings("serial")
public class CodigoUniqueViolationException extends RuntimeException {

	 public CodigoUniqueViolationException(String message) {
		 super(message);
	 }
}
