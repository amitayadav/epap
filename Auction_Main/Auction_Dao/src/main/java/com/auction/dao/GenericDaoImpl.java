package com.auction.dao;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

public class GenericDaoImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements GenericDao<T, ID> {

	private EntityManager entityManager;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public GenericDaoImpl(JpaEntityInformation entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		// Keep the EntityManager around to used from the newly introduced methods.
		this.entityManager = entityManager;
	}

	@SuppressWarnings("hiding")
	@Override
	public <T> void refresh(T entity) {
		Session session = (Session) entityManager.getDelegate();
		session.evict(entity);
		//entityManager.refresh(entity);
	}

}