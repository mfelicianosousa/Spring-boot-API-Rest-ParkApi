package br.com.mfsdevsystem.parkapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.mfsdevsystem.parkapi.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Optional<Usuario> findByUsername(String username);

	@Query("select u.role from Usuario u where u.username like :username")
	Usuario.Role findRoleByUsername(String username);

}
