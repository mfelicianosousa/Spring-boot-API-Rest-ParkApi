package br.com.mfsdevsystem.parkapi.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.mfsdevsystem.parkapi.entity.Vaga;
import br.com.mfsdevsystem.parkapi.exception.CodigoUniqueViolationException;
import br.com.mfsdevsystem.parkapi.exception.EntityNotFoundException;
import br.com.mfsdevsystem.parkapi.repository.VagaRepository;

@Service
public class VagaService {


	private final VagaRepository vagaRepository;
	
	public VagaService(VagaRepository vagaRepository) {
		this.vagaRepository = vagaRepository;
		
	}
	
	@Transactional
	public Vaga save(Vaga vaga) {
		try {
			
			return vagaRepository.save(vaga);
			
		} catch ( DataIntegrityViolationException ex) {
			throw new CodigoUniqueViolationException( 
					String.format("Vaga com código '%s' já cadastrada"));
		}
	}
	
	@Transactional(readOnly = true)
	public Vaga searchByCode( String codigo ) {
		
		return vagaRepository.findByCodigo( codigo ).orElseThrow(
				() -> new EntityNotFoundException( 
						String.format("Vaga com código '%s' não foi encontrada", codigo ))
			);
	}

	public Vaga searchForFreeVacancy() {
	
		return vagaRepository.findFirstByStatus( Vaga.StatusVaga.LIVRE ).orElseThrow(
				() -> new EntityNotFoundException( 
						String.format("Nenhuma vaga livre foi encontrada" ))
			);
	}
}
