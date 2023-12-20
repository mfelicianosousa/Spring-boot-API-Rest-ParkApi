package br.com.mfsdevsystem.parkapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.mfsdevsystem.parkapi.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
