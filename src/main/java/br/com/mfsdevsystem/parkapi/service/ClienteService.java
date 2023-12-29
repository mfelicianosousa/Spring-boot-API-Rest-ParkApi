package br.com.mfsdevsystem.parkapi.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.mfsdevsystem.parkapi.entity.Cliente;
import br.com.mfsdevsystem.parkapi.exception.EntityNotFoundException;
import br.com.mfsdevsystem.parkapi.repository.ClienteRepository;
import br.com.mfsdevsystem.parkapi.repository.projection.ClienteProjection;
import br.com.mfsdevsystem.parkapi.web.exception.CpfUniqueViolationException;


@Service
public class ClienteService {

	private final ClienteRepository clienteRepository;
	
	public ClienteService ( ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}
	
	@Transactional
	public Cliente salvar(Cliente cliente) {
		try {
			return clienteRepository.save( cliente );
		} catch ( DataIntegrityViolationException ex) {
			throw new CpfUniqueViolationException( 
					String.format( "CPF '%s' não pode ser cadastrado, já existe no sistema", cliente.getCpf() )
			);
		}
	}

	@Transactional( readOnly = true)
	public Cliente findById(Long id) {
		
		return clienteRepository.findById( id ).orElseThrow(
				() -> new EntityNotFoundException( String.format( "Cliente id =%s não encontrado.", id ))
		);
	}

	/*
	@Transactional( readOnly = true)
	public Page<Cliente> buscarTodos(Pageable pageable) {
		return clienteRepository.findAll( pageable );
	}
	*/
	
	@Transactional( readOnly = true)
	public Page<ClienteProjection> getAllPageable( Pageable pageable ) {
		return clienteRepository.findAllPageable( pageable );
	}

	@Transactional( readOnly = true)
	public Cliente findbyUserId( Long id ) {
		return clienteRepository.findByUsuarioId( id ) ;
	}

	@Transactional( readOnly = true)
	public Cliente searchByCpf(String cpf) {
		
		return clienteRepository.findByCpf( cpf ).orElseThrow(
				()-> new EntityNotFoundException(
						String.format("Cliente com CPF '%s' não encontrado", cpf ))
				);
				
				
	}
	
}
