package br.com.mfsdevsystem.parkapi.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UsuarioPasswordDto {

	
	
	@NotBlank
	@Size(min = 6, max = 8)
	private String currentPassword;
	
	@NotBlank
	@Size(min = 6, max = 8)
	private String newPassword;
	
	@NotBlank
	@Size(min = 6, max = 8)
	private String confirmPassword;
	 
	public UsuarioPasswordDto() {
		 
	}

	public UsuarioPasswordDto(String currentPassword, String newPassword, String confirmPassword) {
		this.currentPassword = currentPassword;
		this.newPassword = newPassword;
		this.confirmPassword = confirmPassword;
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	@Override
	public String toString() {
		return "UsuarioPasswordDto [currentPassword=" + currentPassword + ", newPassword=" + newPassword
				+ ", confirmPassword=" + confirmPassword + "]";
	}
	 
}
