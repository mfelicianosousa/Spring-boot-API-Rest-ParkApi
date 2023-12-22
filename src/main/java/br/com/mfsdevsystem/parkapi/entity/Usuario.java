package br.com.mfsdevsystem.parkapi.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@SuppressWarnings("serial")
@Entity
@EntityListeners( AuditingEntityListener.class)
@Table(name = "usuarios")
public class Usuario implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	
	@Column(name = "username", nullable = false, unique = true, length=100)
	private String username;
	
	@Column(name = "password", nullable = false, length=200)
	private String password;
	
	
	//@Column(name = "role", nullable = false, length=25)
	
	@Enumerated(EnumType.STRING)
	//@Column(name="role", nullable = false, length = 25)
	//private UserRole role = UserRole.ROLE_CLIENT;
	@Column(name = "role", nullable = false, length = 25)
	 private Role role = Role.ROLE_CLIENTE;
	
	//private Role role;
	
	@CreatedDate
	@Column(name = "data_criacao")
	private LocalDateTime dataCriacao;
	
	@LastModifiedDate
	@Column(name = "data_modificacao")
	private LocalDateTime dataModificacao;
	
	@CreatedBy
	@Column(name = "criado_por")
	private String criadoPor;
	
	@LastModifiedBy
	@Column(name = "modificado_por")
	private String modificadoPor;
	
	public enum Role {
        ROLE_ADMIN, ROLE_CLIENTE
    }
	
	/*
	public enum Role{
		ROLE_ADMIN, ROLE_CLIENTE;
		
		@JsonCreator
        public static Role fromValue(String value) {
            String upperVal = value.toUpperCase();
            for (Role enumValue : Role.values()) {
                if (enumValue.name().equals(upperVal)) {
                    return enumValue;
                }
            }
            throw new IllegalArgumentException("Bad input [" + value + "]");
        }
	}
   */
	
	public Usuario() {
	}
	
	public Usuario(Long id, String username, String password, Role role, LocalDateTime dataCriacao,
			LocalDateTime dataModificacao, String criadoPor, String modificadoPor) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.role = role;
		this.dataCriacao = dataCriacao;
		this.dataModificacao = dataModificacao;
		this.criadoPor = criadoPor;
		this.modificadoPor = modificadoPor;
	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}


	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}


	public LocalDateTime getDataModificacao() {
		return dataModificacao;
	}


	public void setDataModificacao(LocalDateTime dataModificacao) {
		this.dataModificacao = dataModificacao;
	}


	public String getCriadoPor() {
		return criadoPor;
	}


	public void setCriadoPor(String criadoPor) {
		this.criadoPor = criadoPor;
	}


	public String getModificadoPor() {
		return modificadoPor;
	}


	public void setModificadoPor(String modificadoPor) {
		this.modificadoPor = modificadoPor;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + "]";
	}
	
	
}
