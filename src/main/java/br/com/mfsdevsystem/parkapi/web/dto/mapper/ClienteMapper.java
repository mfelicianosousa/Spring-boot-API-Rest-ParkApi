package br.com.mfsdevsystem.parkapi.web.dto.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import br.com.mfsdevsystem.parkapi.entity.Cliente;
import br.com.mfsdevsystem.parkapi.web.dto.ClienteCreateDto;
import br.com.mfsdevsystem.parkapi.web.dto.ClienteResponseDto;

public class ClienteMapper {

	public static Cliente toCliente( ClienteCreateDto dto ) {
		return new ModelMapper().map( dto, Cliente.class ) ;
	}
	
	public static ClienteResponseDto toDto(Cliente cliente) {
	    
	    return new ModelMapper().map( cliente, ClienteResponseDto.class);
	}
	
	public static List<ClienteResponseDto> toListDto( List<Cliente> clientes){
		
		return clientes.stream().map( cliente-> toDto(cliente)).collect(Collectors.toList());
	}
}
