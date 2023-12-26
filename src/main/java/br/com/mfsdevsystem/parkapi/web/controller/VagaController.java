package br.com.mfsdevsystem.parkapi.web.controller;


import java.net.URI;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.mfsdevsystem.parkapi.entity.Vaga;
import br.com.mfsdevsystem.parkapi.service.VagaService;
import br.com.mfsdevsystem.parkapi.web.dto.ClienteResponseDto;
import br.com.mfsdevsystem.parkapi.web.dto.VagaCreateDto;
import br.com.mfsdevsystem.parkapi.web.dto.VagaResponseDto;
import br.com.mfsdevsystem.parkapi.web.dto.mapper.VagaMapper;
import br.com.mfsdevsystem.parkapi.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag( name = "Vagas", description = "Contém todas as operações relativas ao recurso de uma vaga.")
@RestController
@RequestMapping("api/v1/vagas")
public class VagaController {
	
	private final VagaService vagaService;
		
	public VagaController( VagaService vagaService) {
		this.vagaService = vagaService;
	}

	@Operation( summary="Criar uma nova vaga.", description="Recurso para criar uma nova vaga"+
	      " Requisição exige uso de um bearer token. Acesso restrito a Role='ADMIN'",
			security = @SecurityRequirement(name="security"),
			responses = {
					@ApiResponse( responseCode = "201", description ="Vaga criada com sucesso.",
							headers = @Header(name= HttpHeaders.LOCATION, description="URL da vaga criada")),		
				   @ApiResponse( responseCode = "409", description = "Vaga já cadastrada",
					    	content = @Content(mediaType="application/json; charset=UTF-8",
					    	schema = @Schema( implementation = ErrorMessage.class ))),
					@ApiResponse( responseCode = "422", description = "Vaga não processada por dados de entrada inválidos",
					    	content = @Content(mediaType="application/json; charset=UTF-8",
					    	schema = @Schema( implementation = ErrorMessage.class )))	
			}		
	)
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> create(@RequestBody @Valid VagaCreateDto dto){
		Vaga vaga = VagaMapper.toVaga(dto);
		vagaService.save(vaga);
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequestUri().path("/{codigo}")
				.buildAndExpand(vaga.getCodigo())
				.toUri();
		return ResponseEntity.created(location).build();
	}
	
	@Operation( summary="Localizar uma vaga.", description="Recurso para retornar uma vaga pelo seu código."
			+ "Requisição exige uso de um bearer token. Acesso restrito a Role='ADMIN'",
			security = @SecurityRequirement(name="security"),
			responses = {
					@ApiResponse( responseCode = "200", description ="Vaga localizada com sucesso.",
							content = @Content(mediaType="application/json; charset=UTF-8",
							schema = @Schema(implementation = VagaResponseDto.class))),
					@ApiResponse( responseCode = "404", description = "Vaga não Localizada",
		        			content = @Content(mediaType="application/json; charset=UTF-8",
		        			schema = @Schema( implementation = ErrorMessage.class )))
			}		
	)
	@GetMapping("/{codigo}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<VagaResponseDto> getByCode(@PathVariable String codigo){
	
		Vaga vaga = vagaService.searchByCode(codigo);
		return ResponseEntity.ok(VagaMapper.toDto(vaga));
	}
}
