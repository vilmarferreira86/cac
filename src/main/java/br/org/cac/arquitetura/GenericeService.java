package br.org.cac.arquitetura;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import com.google.common.base.Throwables;

public class GenericeService<E, R extends JpaRepository<E, Integer>, D extends IDTO> implements IService<E,D>{
	
	@Autowired
	private R repository;	
	
	

	@Override
	public List<?> findAll() {
		List<IDTO> dtoList = new ArrayList<>();
		
		if(getDtoClass() == null) {
			return null;
		}
		
		for (E entity : (List<E>) repository.findAll()) {
			dtoList.add(UtilsDTO.entityToDTO(entity, getDtoClass()));
		}
		
		return dtoList;
	}
	
	private Class<? extends IDTO> getDtoClass() {
		ParameterizedType superClass = (ParameterizedType) getClass().getGenericSuperclass();
		@SuppressWarnings("unchecked")
		Class<D> type = (Class<D>) superClass.getActualTypeArguments()[2];
		try {
			return type.newInstance().getClass();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private Class<?> getEntityClass() {
		ParameterizedType superClass = (ParameterizedType) getClass().getGenericSuperclass();
		@SuppressWarnings("unchecked")
		Class<E> type = (Class<E>) superClass.getActualTypeArguments()[0];
		try {
			return type.newInstance().getClass();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public IDTO findById(Integer id) {
		if(repository.findById(id).isPresent()) {
			return UtilsDTO.entityToDTO(repository.findById(id).get(), getDtoClass());
		}		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public D create(D object) {
		E entityForSave = (E) UtilsDTO.dtoToEntity(object, getEntityClass());
		E entitySaved = repository.saveAndFlush((E)entityForSave);
		return (D) UtilsDTO.entityToDTO(entitySaved, getDtoClass());
	}

	@SuppressWarnings("unchecked")
	@Override
	public D update(Integer id, D object) {
		
		if(repository.findById(id).isPresent()) {			
			E entityTarget = (E) repository.findById(id).get();
			E entitySource = (E) UtilsDTO.dtoToEntity(object, getEntityClass());
			
			try {
				BeanUtils.copyProperties(entitySource, entityTarget, "id");
				
			}catch (Exception e) {
				throw Throwables.propagate(e);
			}
			
			return (D) UtilsDTO.entityToDTO((E) repository.saveAndFlush(entityTarget), getDtoClass());
		}
		

		return null;
	}

	@Override
	public boolean delete(Integer id) {
		if(!repository.findById(id).isPresent())
			return false;
		
		repository.delete(repository.findById(id).get());
		return true;
	}

	@Override
	public JpaRepository<E, Integer> getRepository() {
		return this.repository;
	}

	

}
