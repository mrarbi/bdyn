package com.orbit.dynamix.dao;

import java.io.Serializable;
import java.util.List;

public interface IGenericDAO<T, PK extends Serializable> {

	public PK create(final T entity);

	public void saveOrUpdate(final T entity);

	public T findOne(final PK id);

	public List<T> findAll();

	public void update(final T entity);

	public T updateAndGet(final T entity);

	public void delete(final T entity);

	public void deleteById(final PK entityId);

}