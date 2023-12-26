package br.com.mfsdevsystem.parkapi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.mfsdevsystem.parkapi.entity.Cliente;
import br.com.mfsdevsystem.parkapi.repository.projection.ClienteProjection;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{

	@Query("select c from Cliente c")
	Page<ClienteProjection> findAllPageable(Pageable pageable);

	Cliente findByUsuarioId(Long id);
}
