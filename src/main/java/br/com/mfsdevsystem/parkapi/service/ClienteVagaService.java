package br.com.mfsdevsystem.parkapi.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.mfsdevsystem.parkapi.entity.ClienteVaga;
import br.com.mfsdevsystem.parkapi.exception.EntityNotFoundException;
import br.com.mfsdevsystem.parkapi.repository.ClienteVagaRepository;
import br.com.mfsdevsystem.parkapi.repository.projection.ClienteVagaProjection;

@Service
public class ClienteVagaService {

	private final ClienteVagaRepository repository;
	
	public ClienteVagaService(ClienteVagaRepository repository) {
		this.repository = repository;
	}
	
	@Transactional
	public ClienteVaga save ( ClienteVaga clienteVaga ) {
		return repository.save( clienteVaga );
	}

	@Transactional(readOnly = true)
	public ClienteVaga searchByRecibo(String recibo) {

		return repository.findByReciboAndDataSaidaIsNull( recibo).orElseThrow(
				() -> new EntityNotFoundException (
						String.format("Recibo '%s' não encontrado no sistema ou check-out já realizado", recibo )
				 ))	;
	}

	@Transactional(readOnly = true)
	public Long getTotalDeVezesEstacionamentoCompleto( String cpf ) {
	
		return repository.countByClienteCpfAndDataSaidaIsNotNull( cpf );
	}

	@Transactional(readOnly = true)
	public Page<ClienteVagaProjection> buscarTodosPorClienteCpf(String cpf, Pageable pageable) {
		
		return repository.findAllByClienteCpf(cpf, pageable);
	}

	@Transactional(readOnly = true)
	public Page<ClienteVagaProjection> buscarTodosPorUsuarioId(Long id, Pageable pageable) {
		// TODO Auto-generated method stub
		return repository.findAllByClienteUsuarioId( id, pageable );
	}
	
}
