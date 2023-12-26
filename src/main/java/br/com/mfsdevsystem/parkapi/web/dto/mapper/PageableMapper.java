package br.com.mfsdevsystem.parkapi.web.dto.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.data.domain.Page;

import br.com.mfsdevsystem.parkapi.web.dto.PageableDto;


public class PageableMapper {

    private PageableMapper() {
    }

    public static PageableDto toDto( Page<?> page) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setFieldMatchingEnabled(true).setFieldAccessLevel( Configuration.AccessLevel.PRIVATE);
        return modelMapper.map(page, PageableDto.class);
    }
}
