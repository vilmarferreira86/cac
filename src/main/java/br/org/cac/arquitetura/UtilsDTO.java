package br.org.cac.arquitetura;

import org.modelmapper.ModelMapper;

public class UtilsDTO {
	private static ModelMapper mapper = new ModelMapper();
	
	public static ModelMapper mapper() {
		return mapper;
	}
	
	public static IDTO entityToDTO(Object source, Class<? extends IDTO> destinationType) {
		return mapper.map(source, destinationType);
	}
	
	public static Object dtoToEntity(IDTO source, Class<?> destinationType) {
		return mapper.map(source, destinationType);
	}
}
