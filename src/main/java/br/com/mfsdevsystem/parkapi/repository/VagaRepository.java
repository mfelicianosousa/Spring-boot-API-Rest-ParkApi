package br.com.mfsdevsystem.parkapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.mfsdevsystem.parkapi.entity.Vaga;

public interface VagaRepository extends JpaRepository<Vaga, Long> {

	
	Optional<Vaga> findByCodigo(String codigo);

	Optional<Vaga> findFirstByStatus(Vaga.StatusVaga vaga);

}
