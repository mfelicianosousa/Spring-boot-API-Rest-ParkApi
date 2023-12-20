package br.com.mfsdevsystem.parkapi.service;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.mfsdevsystem.parkapi.entity.Usuario;
import br.com.mfsdevsystem.parkapi.exception.EntityNotFoundException;
import br.com.mfsdevsystem.parkapi.exception.UsernameUniqueViolationException;
import br.com.mfsdevsystem.parkapi.repository.UsuarioRepository;

@Service
public class UsuarioService { 
	
	private final UsuarioRepository usuarioRepository ;

	public UsuarioService( UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	@Transactional
	public Usuario salvar(Usuario usuario) {
			
		try {
			 return usuarioRepository.save(usuario);
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
	public Usuario updatePassword(Long id, String currentPassword, String newPassword, String confirmPassword) {
		
		if (!newPassword.equals(confirmPassword)) {
			
			throw new RuntimeException("Nova senha não confere com a confirmação de senha.");	
		}
		
		Usuario user = findById(id);
		
		if (!user.getPassword().equals(currentPassword)){
			
			throw new RuntimeException("Sua senha não confere.");
			
		}
		
		user.setPassword(newPassword);
		
		return user;
	}

	@Transactional(readOnly = true)
	public List<Usuario> searchAll() {
		return usuarioRepository.findAll();
	}
	
	

}
