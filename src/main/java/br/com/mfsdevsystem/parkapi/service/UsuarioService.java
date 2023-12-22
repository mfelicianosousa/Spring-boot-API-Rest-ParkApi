package br.com.mfsdevsystem.parkapi.service;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.mfsdevsystem.parkapi.entity.Usuario;
import br.com.mfsdevsystem.parkapi.exception.EntityNotFoundException;
import br.com.mfsdevsystem.parkapi.exception.PasswordInvalidException;
import br.com.mfsdevsystem.parkapi.exception.UsernameUniqueViolationException;
import br.com.mfsdevsystem.parkapi.repository.UsuarioRepository;

@Service
public class UsuarioService { 
	
	private final UsuarioRepository usuarioRepository ;
	private final PasswordEncoder passwordEncoder;

	public UsuarioService( UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
		this.usuarioRepository = usuarioRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public Usuario salvar(Usuario usuario) {
			
		try {
			 usuario.setPassword( passwordEncoder.encode( usuario.getPassword() ) );
			 return usuarioRepository.save( usuario );
		} catch ( DataIntegrityViolationException ex ) {
			throw new UsernameUniqueViolationException( String.format("Username {%s} já cadastrado, ",usuario.getUsername()));
		}
		
		
	}  
    /*
	@Transactional(readOnly = true)
	public Usuario findById(Long id) {
		
		return usuarioRepository.findById(id).orElseThrow(
				()->new RuntimeException("Usuário não encontrado.")
				);
	}
	*/
	@Transactional(readOnly = true)
	public Usuario findById(Long id) {
		
		return usuarioRepository.findById( id ).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuário id=%s não encontrado", id))
        );
	}

	@Transactional
	public Usuario updatePassword( Long id, String currentPassword, String newPassword, String confirmPassword) {
		
		if ( !newPassword.equals( confirmPassword ) ) {
			
			throw new PasswordInvalidException("Nova senha não confere com a confirmação de senha.");	
		}
		
		Usuario usuario = findById( id ) ;
		
		//if (!user.getPassword().equals(currentPassword)){
		if ( !passwordEncoder.matches( currentPassword, usuario.getPassword())) {
		
			throw new PasswordInvalidException("Sua senha não confere.");		
		}
		
		usuario.setPassword( passwordEncoder.encode( newPassword ));
		
		return usuario;
	}

	@Transactional(readOnly = true)
	public List<Usuario> searchAll() {
		return usuarioRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Usuario buscarPorUsername(String username) {
		
		return usuarioRepository.findByUsername( username ).orElseThrow(
                () -> new EntityNotFoundException( String.format( "Usuário com %s não encontrado", username)));
	}

	@Transactional(readOnly = true)
	public Usuario.Role buscarRolePorUsername(String username) {
		
		return usuarioRepository.findRoleByUsername( username) ;
	}
	
}
