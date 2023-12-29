package br.com.mfsdevsystem.parkapi.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.mfsdevsystem.parkapi.entity.Cliente;
import br.com.mfsdevsystem.parkapi.entity.ClienteVaga;
import br.com.mfsdevsystem.parkapi.entity.Vaga;
import br.com.mfsdevsystem.parkapi.util.EstacionamentoUtils;

@Service
public class EstacionamentoService {

	  private final ClienteVagaService clienteVagaService;
	  private final ClienteService clienteService;
	  private final VagaService vagaService;
	  
	  public EstacionamentoService ( ClienteVagaService clienteVagaService,
														     ClienteService clienteService,
															 VagaService vagaService ){
		  this.clienteVagaService = clienteVagaService;
		  this.clienteService = clienteService;
		  this.vagaService = vagaService ;
	  }
	  
	  @Transactional
	  public ClienteVaga checkIn(ClienteVaga clienteVaga) {

		  Cliente cliente = clienteService.searchByCpf( clienteVaga.getCliente().getCpf());
		  clienteVaga.setCliente(cliente);
		  Vaga vaga = vagaService.searchForFreeVacancy();  // buscar por vaga livre
		  
		  vaga.setStatus(Vaga.StatusVaga.OCUPADA);
		  clienteVaga.setVaga(vaga);
		  clienteVaga.setDataEntrada( LocalDateTime.now());
		  clienteVaga.setRecibo( EstacionamentoUtils.gerarRecibo());
		  return clienteVagaService.save(clienteVaga);
		  
	  }
	  
	  

	@Transactional
	public ClienteVaga checkout(String recibo) {
	
		ClienteVaga clienteVaga = clienteVagaService.searchByRecibo( recibo );
		
		LocalDateTime dataSaida = LocalDateTime.now();
		
		BigDecimal valor = EstacionamentoUtils.calcularCusto( clienteVaga.getDataEntrada(), dataSaida);
		clienteVaga.setValor(valor);
		
		Long totalDeVezes = clienteVagaService.getTotalDeVezesEstacionamentoCompleto(clienteVaga.getCliente().getCpf());
		
		BigDecimal desconto = EstacionamentoUtils.calcularDesconto(valor, totalDeVezes);
		
		clienteVaga.setDesconto( desconto );
		
		clienteVaga.setDataSaida(dataSaida);
		clienteVaga.getVaga().setStatus( Vaga.StatusVaga.LIVRE );
		
		return clienteVagaService.save( clienteVaga ) ;

	}
}
