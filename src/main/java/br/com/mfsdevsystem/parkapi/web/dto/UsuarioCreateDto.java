package br.com.mfsdevsystem.parkapi.web.dto;

import java.util.Objects;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UsuarioCreateDto {

	@NotBlank
	@Email(regexp= "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$", message = "Formato do email inválido.")
	private String username;
	
	@NotBlank
	@Size(min = 6, max = 8)
	private String password;
	
	public UsuarioCreateDto() {
		
	}

	public UsuarioCreateDto(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		return Objects.hash(username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioCreateDto other = (UsuarioCreateDto) obj;
		return Objects.equals(username, other.username);
	}
	
}
