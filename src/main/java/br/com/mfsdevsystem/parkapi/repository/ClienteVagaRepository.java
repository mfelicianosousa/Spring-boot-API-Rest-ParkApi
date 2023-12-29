package br.com.mfsdevsystem.parkapi.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.mfsdevsystem.parkapi.entity.ClienteVaga;
import br.com.mfsdevsystem.parkapi.repository.projection.ClienteVagaProjection;

public interface ClienteVagaRepository extends JpaRepository< ClienteVaga, Long>{

	Optional<ClienteVaga> findByReciboAndDataSaidaIsNull(String recibo);

	Long countByClienteCpfAndDataSaidaIsNotNull(String cpf);

	Page<ClienteVagaProjection> findAllByClienteCpf(String cpf, Pageable pageable);

	Page<ClienteVagaProjection> findAllByClienteUsuarioId(Long id, Pageable pageable);
	
}
