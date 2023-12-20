package br.com.mfsdevsystem.parkapi.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {
	
	private final UsuarioService usuarioService;
	
	public UsuarioController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
	
	@PostMapping
	public ResponseEntity<UsuarioResponseDto> created(@Valid @RequestBody UsuarioCreatedDto usuarioCreatedDto){
		
		Usuario user = usuarioService.salvar( UsuarioMapper.toUsuario( usuarioCreatedDto ));
		return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDto(user));
	}
	
	@GetMapping("/{id}")
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
	@PatchMapping("/{id}")
	public ResponseEntity<Void> updatePassword(@PathVariable Long id,@Valid @RequestBody UsuarioPasswordDto dto){
		
		Usuario user = usuarioService.updatePassword( id, dto.getCurrentPassword(),dto.getNewPassword(), dto.getConfirmPassword() );
		return ResponseEntity.noContent().build();
	}
	
	
	@GetMapping
	public ResponseEntity<List<UsuarioResponseDto>> getSearchAll(){
		
		List<Usuario> users = usuarioService.searchAll();
		
		return ResponseEntity.ok(UsuarioMapper.toListDto(users));
	}


}
