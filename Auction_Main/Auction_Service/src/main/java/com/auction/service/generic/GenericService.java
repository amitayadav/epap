package com.auction.service.generic;

import java.util.List;



public interface GenericService<T,ID> {

	/*public T save(T bean);
	public T update(T bean) ;
	public void delete(ID id);
	public List<T> findAll();
	public T findById(ID id);*/
	
	
	//********** CrudRepository Method stuff ************** /*
	ID save(T bean);
	
	/**
	 * Retrieves an entity by its id.
	 * 
	 * @param id must not be {@literal null}.
	 * @return the entity with the given id or {@literal null} if none found
	 * @throws IllegalArgumentException if {@code id} is {@literal null}
	 */
	T findById(ID id);
	
	/**
	 * Returns whether an entity with the given id exists.
	 * 
	 * @param id must not be {@literal null}.
	 * @return true if an entity with the given id exists, {@literal false} otherwise
	 * @throws IllegalArgumentException if {@code id} is {@literal null}
	 */
	boolean exists(ID id);
	
	/**
	 * Returns all instances of the type.
	 * 
	 * @return all entities
	 */
	List<T> findAll();
	
	/**
	 * Returns all instances of the type with the given IDs.
	 * 
	 * @param ids
	 * @return
	 */
	List<T> findAll(Iterable<ID> ids);
	
	/**
	 * Returns the number of entities available.
	 * 
	 * @return the number of entities
	 */
	long count();
	
	/**
	 * Deletes a given entity.
	 * 
	 * @param entity
	 * @throws IllegalArgumentException in case the given entity is {@literal null}.
	 */
	void delete(T bean);
	
	/**
	 * Returns all entities sorted by the given options.
	 * 
	 * @param sort
	 * @return all entities sorted by the given options
	 */
//	Iterable<T> findAll(Sort sort);

	/**
	 * Returns a {@link Page} of entities meeting the paging restriction provided in the {@code Pageable} object.
	 * 
	 * @param pageable
	 * @return a page of entities
	 */
//	Page<T> findAll(Pageable pageable);
	
	ID refresh(ID id);
	
}