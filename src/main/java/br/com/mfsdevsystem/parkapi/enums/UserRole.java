package br.com.mfsdevsystem.parkapi.enums;
public enum UserRole {

	ROLE_ADMIN("ROLE_ADMIN","Administrador"),
	ROLE_USER("ROLE_USER","Usuario"),
	ROLE_CLIENT("ROLE_CLIENT","Cliente");
	
	
	private String role;
	private String descricao;
	
	UserRole(String role,String descricao){
	
		this.role = role;
		this.descricao = descricao;
	}
	
	
	public String getRole() {
		return role;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
}
