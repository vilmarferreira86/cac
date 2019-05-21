package br.org.cac.arquitetura;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.org.cac.arquitetura.IDTO;


public interface IService<E, T extends IDTO > {
		
	public abstract List<?> findAll();
	
	public abstract IDTO findById(Integer id);
	
	public abstract T create(T object);
	
	public abstract T update(Integer id, T object);
	
	public abstract boolean delete(Integer id);	
	
	public JpaRepository<E, Integer> getRepository();

}
