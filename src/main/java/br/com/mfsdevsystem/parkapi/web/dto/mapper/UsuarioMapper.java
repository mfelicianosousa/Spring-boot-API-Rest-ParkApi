package br.com.mfsdevsystem.parkapi.web.dto.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import br.com.mfsdevsystem.parkapi.entity.Usuario;
import br.com.mfsdevsystem.parkapi.web.dto.UsuarioCreatedDto;
import br.com.mfsdevsystem.parkapi.web.dto.UsuarioResponseDto;

public class UsuarioMapper {

	public static Usuario toUsuario(UsuarioCreatedDto usuarioCreatedDto) {
		return new ModelMapper().map( usuarioCreatedDto, Usuario.class ) ;
	}
	
	public static UsuarioResponseDto toDto(Usuario usuario) {
		
		String role = usuario.getRole().name().substring("ROLE_".length());
		PropertyMap< Usuario, UsuarioResponseDto> props = new PropertyMap<Usuario, UsuarioResponseDto>(){
			@Override
			protected void configure() {
				map().setRole(role);	
			}
		};
		
		ModelMapper mapper = new ModelMapper();
	    mapper.addMappings( props) ;
	    
	    return mapper.map( usuario, UsuarioResponseDto.class);
	}
	
	public static List<UsuarioResponseDto> toListDto( List<Usuario> usuarios){
		
		return usuarios.stream().map( user-> toDto(user)).collect(Collectors.toList());
	}
	
}
