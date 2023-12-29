package br.com.mfsdevsystem.parkapi.web.dto.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;

import br.com.mfsdevsystem.parkapi.entity.ClienteVaga;
import br.com.mfsdevsystem.parkapi.web.dto.EstacionamentoCreateDto;
import br.com.mfsdevsystem.parkapi.web.dto.EstacionamentoResponseDto;

public class ClienteVagaMapper {

	public static ClienteVaga toClienteVaga( EstacionamentoCreateDto dto ) {
		ModelMapper modelMapper = new ModelMapper();
	    modelMapper.getConfiguration().setFieldMatchingEnabled(true).setFieldAccessLevel( Configuration.AccessLevel.PRIVATE);
	 
		return new ModelMapper().map( dto, ClienteVaga.class ) ;
	}
	
	public static EstacionamentoResponseDto toDto(ClienteVaga clienteVaga) {
		ModelMapper modelMapper = new ModelMapper();
	    modelMapper.getConfiguration().setFieldMatchingEnabled(true).setFieldAccessLevel( Configuration.AccessLevel.PRIVATE);    
	    return new ModelMapper().map( clienteVaga, EstacionamentoResponseDto.class);
	}
	
	public static List<EstacionamentoResponseDto> toListDto( List<ClienteVaga> clientesVagas){
		
		return clientesVagas.stream().map( clienteVaga-> toDto(clienteVaga)).collect(Collectors.toList());
	}
}
