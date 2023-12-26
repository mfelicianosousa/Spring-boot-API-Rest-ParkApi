package br.com.mfsdevsystem.parkapi.web.controller;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mfsdevsystem.parkapi.entity.Cliente;
import br.com.mfsdevsystem.parkapi.jwt.JwtUserDetails;
import br.com.mfsdevsystem.parkapi.repository.projection.ClienteProjection;
import br.com.mfsdevsystem.parkapi.service.ClienteService;
import br.com.mfsdevsystem.parkapi.service.UsuarioService;
import br.com.mfsdevsystem.parkapi.web.dto.ClienteCreateDto;
import br.com.mfsdevsystem.parkapi.web.dto.ClienteResponseDto;
import br.com.mfsdevsystem.parkapi.web.dto.PageableDto;
import br.com.mfsdevsystem.parkapi.web.dto.mapper.ClienteMapper;
import br.com.mfsdevsystem.parkapi.web.dto.mapper.PageableMapper;
import br.com.mfsdevsystem.parkapi.web.exception.ErrorMessage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag( name = "Clientes", description = "Contém todas as operações relativas ao recurso de um cliente.")
@RestController
@RequestMapping("api/v1/clientes")
public class ClienteController {


	private final ClienteService clienteService;
	private final UsuarioService usuarioService;
	
	public ClienteController( ClienteService clienteService, UsuarioService usuarioService) {
		this.clienteService = clienteService;
		this.usuarioService = usuarioService;
	}
	
	
	@Operation( summary="Criar um novo cliente.", description="Recurso para criar um novo cliente, vinculado a um"
			+ "usuário cadastrado. Requisição exige uso de um bearer token. Acesso restrito a Role='CLIENTE'",
			security = @SecurityRequirement(name="security"),
			responses = {
					@ApiResponse( responseCode = "201", description ="Cliente criado com sucesso.",
							content = @Content(mediaType="application/json; charset=UTF-8",
							schema = @Schema(implementation = ClienteResponseDto.class))),
					@ApiResponse( responseCode = "403", description = "Cliente não pemitido ao perfil de ADMIN",
		        			content = @Content(mediaType="application/json; charset=UTF-8",
		        			schema = @Schema( implementation = ErrorMessage.class ))),
					@ApiResponse( responseCode = "409", description = "Cliente CPF já possui cadastrado no sistema",
					    	content = @Content(mediaType="application/json; charset=UTF-8",
					    	schema = @Schema( implementation = ErrorMessage.class ))),
					@ApiResponse( responseCode = "422", description = "Cliente não processado por dados de entrada inválidos",
					    	content = @Content(mediaType="application/json; charset=UTF-8",
					    	schema = @Schema( implementation = ErrorMessage.class )))	
			}		
	)
	@PreAuthorize("hasRole('CLIENTE')")
	@PostMapping
	public ResponseEntity<ClienteResponseDto> create ( 
			@RequestBody @Valid ClienteCreateDto dto,
			@AuthenticationPrincipal JwtUserDetails userDetails){
		
			Cliente cliente = ClienteMapper.toCliente( dto );
			cliente.setUsuario( usuarioService.findById( userDetails.getId() ));
			clienteService.salvar( cliente );
			return ResponseEntity.status( 201 ).body( ClienteMapper.toDto( cliente ));
	}
	
	@Operation( summary="Localizar um cliente.", description="Recurso para localizar um cliente pelo ID."
			+ "usuário cadastrado. Requisição exige uso de um bearer token. Acesso restrito a Role='ADMIN'",
			security = @SecurityRequirement(name="security"),
			responses = {
					@ApiResponse( responseCode = "200", description ="Cliente localizado com sucesso.",
							content = @Content(mediaType="application/json; charset=UTF-8",
							schema = @Schema(implementation = ClienteResponseDto.class))),
					@ApiResponse( responseCode = "404", description = "Cliente não encontrado",
		        			content = @Content(mediaType="application/json; charset=UTF-8",
		        			schema = @Schema( implementation = ErrorMessage.class ))),
					@ApiResponse( responseCode = "403", description = "Cliente não permitido ao perfil de CLIENTE",
					    	content = @Content(mediaType="application/json; charset=UTF-8",
					    	schema = @Schema( implementation = ErrorMessage.class )))	
			}		
	)
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/{id}")
	public ResponseEntity<ClienteResponseDto> getById(@PathVariable Long id){
		Cliente cliente = clienteService.findById( id );
		return ResponseEntity.ok(ClienteMapper.toDto(cliente));
	}
	/*
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping
	public ResponseEntity<Page<Cliente>> getAll(Pageable pageable){
		Page<Cliente> clientes = clienteService.buscarTodos( pageable );
		return ResponseEntity.ok( clientes );
	}
	*/
	
	@Operation( summary="Recuperar lista de clientes.", description="Requisição exige uso de um bearer token. Acesso restrito a Role='ADMIN'",
			security = @SecurityRequirement(name="security"),
	        parameters = {		
					@Parameter( in = QUERY, name ="page",
					    content = @Content(schema= @Schema(type = "integer", defaultValue="0")),
					    description = "Representa a página retornada"
					 ),
					@Parameter( in = QUERY, name ="size",
				    content = @Content(schema= @Schema(type = "integer", defaultValue="20")),
				    description = "Representa o total de elementos por página"
				    ),
					@Parameter(in = QUERY, name = "sort", hidden = true,
                    array = @ArraySchema(schema = @Schema(type = "string", defaultValue = "nome,asc")),
                    description = "Representa a ordenação dos resultados. Aceita multiplos critérios de ordenação são suportados."
				),	
					
			},
			responses = {
					@ApiResponse( responseCode = "200", description ="Clientes recuperados com sucesso.",
							content = @Content(mediaType="application/json; charset=UTF-8",
							schema = @Schema(implementation = ClienteResponseDto.class))),
					@ApiResponse( responseCode = "403", description = "Clientes não permitido ao perfil de CLIENTE",
					    	content = @Content(mediaType="application/json; charset=UTF-8",
					    	schema = @Schema( implementation = ErrorMessage.class )))	
			}		
	)
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping
	public ResponseEntity<PageableDto> getAllPageable( @Parameter( hidden = true )
			@PageableDefault( size = 5, sort = {"nome"} ) Pageable pageable ){
		Page<ClienteProjection> clientes = clienteService.getAllPageable( pageable );
		return ResponseEntity.ok( PageableMapper.toDto( clientes ) );
	}
	
	
	@Operation( summary="Recuperar dados do cliente authenticado", description="Requisição exige uso de um bearer token. Acesso restrito a Role='CLIENTE'",
			security = @SecurityRequirement(name="security"),
			responses = {
					@ApiResponse( responseCode = "200", description ="Cliente recuperado com sucesso.",
							content = @Content( mediaType="application/json; charset=UTF-8",
							schema = @Schema( implementation = ClienteResponseDto.class ))),
					@ApiResponse( responseCode = "403", description = " Cliente não pemitido ao perfil de ADMIN",
		        			content = @Content(mediaType="application/json; charset=UTF-8",
		        			schema = @Schema( implementation = ErrorMessage.class )))
			}		
	)
	@GetMapping("/detalhes")
	@PreAuthorize("hasRole('CLIENTE')")
	public ResponseEntity<ClienteResponseDto> getDetalhes(@AuthenticationPrincipal JwtUserDetails userDetails){
		Cliente cliente = clienteService.findbyUserId( userDetails.getId());
	   return ResponseEntity.ok(ClienteMapper.toDto( cliente ));	
	}
}
