package br.com.mfsdevsystem.parkapi.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mfsdevsystem.parkapi.entity.Usuario;
import br.com.mfsdevsystem.parkapi.service.UsuarioService;
import br.com.mfsdevsystem.parkapi.web.dto.UsuarioCreatedDto;
import br.com.mfsdevsystem.parkapi.web.dto.UsuarioPasswordDto;
import br.com.mfsdevsystem.parkapi.web.dto.UsuarioResponseDto;
import br.com.mfsdevsystem.parkapi.web.dto.mapper.UsuarioMapper;
import br.com.mfsdevsystem.parkapi.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


@Tag(name="Usuarios", description="Contém todas as operações relativas os recursos para cadastro, edição e leitura de um usuário." )
@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {
	
	private final UsuarioService usuarioService;
	
	public UsuarioController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
	
	@Operation( summary="Criar um novo usuário", description="Recurso para criar um novo usuário",
			responses = {
					@ApiResponse( responseCode = "201", description ="Recurso criado com sucesso",
					    content = @Content(mediaType="application/json",
					    schema = @Schema(implementation = UsuarioResponseDto.class))),
					@ApiResponse( responseCode = "409", description = "Usuário e-mail já cadastrado no sistema",
					    content = @Content(mediaType="application/json",
					    schema = @Schema( implementation = ErrorMessage.class ))),
					@ApiResponse( responseCode = "422", description = "Recurso não processado por dados de entrada inválidos",
					    content = @Content(mediaType="application/json",
					    schema = @Schema( implementation = ErrorMessage.class )))
			}		
	)
	@PostMapping
	public ResponseEntity<UsuarioResponseDto> created(@Valid @RequestBody UsuarioCreatedDto usuarioCreatedDto){
		
		Usuario user = usuarioService.salvar( UsuarioMapper.toUsuario( usuarioCreatedDto ));
		return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDto(user));
	}
	
	
	
	@Operation( summary="Recuperar um usuário pelo id", description="Requisição exige um Bearer Token. Acesso restrito a ADMIN|CLIENT",
			security =@SecurityRequirement(name =  "security"),
			responses = {
					@ApiResponse( responseCode = "200", description ="Recurso recuperado com sucesso",
					    content = @Content(mediaType="application/json",
					    schema = @Schema(implementation = UsuarioResponseDto.class))),
					@ApiResponse( responseCode = "403", description = "Usuário sem permissão para acessar esse recurso",
					    content = @Content(mediaType="application/json",
					    schema = @Schema( implementation = ErrorMessage.class ))),
					@ApiResponse( responseCode = "404", description = "Recurso não encontrado",
					    content = @Content(mediaType="application/json",
				        schema = @Schema( implementation = ErrorMessage.class )))
			}		
	)
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN') OR ( hasRole('CLIENTE') AND #id == authentication.principal.id )")
	public ResponseEntity<UsuarioResponseDto> getById(@PathVariable Long id){
		
		Usuario user = usuarioService.findById( id );
		return ResponseEntity.ok(UsuarioMapper.toDto(user));
	}
	
	/*@PatchMapping("/{id}")
	public ResponseEntity<UsuarioResponseDto> updatePassword(@PathVariable Long id, @RequestBody UsuarioPasswordDto dto){
		
		Usuario user = usuarioService.updatePassword( id, dto.getCurrentPassword(),dto.getNewPassword(), dto.getConfirmPassword() );
		return ResponseEntity.ok(UsuarioMapper.toDto(user));
	}
	*/
	
	/* change password */
	@Operation( summary="Atualizar senha", description="Requisição exige um Bearer Token. Acesso restrito a ADMIN|CLIENT",
			security =@SecurityRequirement(name =  "security"),
			responses = {
					@ApiResponse( responseCode = "204", description ="Senha atualizada com sucesso",
					    content = @Content(mediaType="application/json",
					    schema = @Schema(implementation = void.class))),
					@ApiResponse( responseCode = "400", description = "Senha não confere",
					    content = @Content(mediaType="application/json",
					    schema = @Schema( implementation = ErrorMessage.class ))),
					@ApiResponse( responseCode = "403", description = "Usuário sem permissão para acessar esse recurso",
					    content = @Content(mediaType="application/json",
					    schema = @Schema( implementation = ErrorMessage.class ))),
					@ApiResponse( responseCode = "404", description = "Recurso não encontrado",
				    	content = @Content(mediaType="application/json",
				    	schema = @Schema( implementation = ErrorMessage.class ))),
					@ApiResponse( responseCode = "422", description = "Campos inválidos ou mal formatados",
				    	content = @Content(mediaType="application/json",
				    	schema = @Schema( implementation = ErrorMessage.class )))
			}		
	)
	@PatchMapping("/{id}")
	@PreAuthorize("hasAnyRole('ADMIN','CLIENTE') AND (#id == authentication.principal.id )")
	public ResponseEntity<Void> updatePassword(@PathVariable Long id,@Valid @RequestBody UsuarioPasswordDto dto){
		
		Usuario user = usuarioService.updatePassword( id, dto.getCurrentPassword(),dto.getNewPassword(), dto.getConfirmPassword() );
		return ResponseEntity.noContent().build();
	}
	
	@Operation( summary="Listar todos os usuário cadastrados", description="Requisição exige um Bearer Token. Acesso restrito a ADMIN",
			security =@SecurityRequirement(name =  "security"),
			responses = {
					@ApiResponse( responseCode = "200", description ="Listagem recuperada com sucesso",
							content = @Content(mediaType="application/json",
					    	array = @ArraySchema(schema = @Schema(implementation = UsuarioResponseDto.class)))),
					@ApiResponse( responseCode = "403", description = "Usuário sem permissão para acessar esse recurso",
				    		content = @Content(mediaType="application/json",
				    		schema = @Schema( implementation = ErrorMessage.class )))
			}		
	)
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<List<UsuarioResponseDto>> getSearchAll(){
		
		List<Usuario> users = usuarioService.searchAll();
		
		return ResponseEntity.ok(UsuarioMapper.toListDto(users));
	}


}
