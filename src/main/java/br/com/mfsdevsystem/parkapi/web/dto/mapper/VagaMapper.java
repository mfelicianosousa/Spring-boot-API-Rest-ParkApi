package br.com.mfsdevsystem.parkapi.web.dto.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;

import br.com.mfsdevsystem.parkapi.entity.Vaga;
import br.com.mfsdevsystem.parkapi.web.dto.VagaCreateDto;
import br.com.mfsdevsystem.parkapi.web.dto.VagaResponseDto;

public class VagaMapper {

	 private VagaMapper() {
     }
	 public static Vaga toVaga( VagaCreateDto dto) {
	        ModelMapper modelMapper = new ModelMapper();
	        modelMapper.getConfiguration().setFieldMatchingEnabled(true).setFieldAccessLevel( Configuration.AccessLevel.PRIVATE);
	        return modelMapper.map(dto, Vaga.class);
	  }
	 
	 public static VagaResponseDto toDto( Vaga vaga) {
	        ModelMapper modelMapper = new ModelMapper();
	        modelMapper.getConfiguration().setFieldMatchingEnabled(true).setFieldAccessLevel( Configuration.AccessLevel.PRIVATE);
	        return modelMapper.map(vaga, VagaResponseDto.class);
	    }
}
