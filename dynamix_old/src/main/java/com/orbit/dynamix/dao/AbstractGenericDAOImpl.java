package com.orbit.dynamix.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public abstract class AbstractGenericDAOImpl<T, PK extends Serializable> implements IGenericDAO<T, PK> {

	@Autowired
	private SessionFactory sessionFactory;

	private Class<T> clazz;

	protected final void setClazz(final Class<T> clazzToSet) {
		clazz = clazzToSet;
	}

	public PK create(final T entity) {
		return (PK) getCurrentSession().save(entity);
	}

	public final void saveOrUpdate(final T entity) {
		getCurrentSession().saveOrUpdate(entity);
	}

	public final T findOne(final PK id) {
		T t = (T) getCurrentSession().get(clazz, id);

		return t;
	}

	public final List<T> findAll() {
		Criteria crit = getCurrentSession().createCriteria(clazz);
		return crit.list();
	}

	public void update(final T entity) {
		getCurrentSession().update(entity);
	}

	public final T updateAndGet(final T entity) {
		return (T) getCurrentSession().merge(entity);
	}

	public final void delete(final T entity) {
		getCurrentSession().delete(entity);
	}

//	public final void deleteById(final PK entityId) {
//		final T entity = findOne(entityId);
//		delete(entity);
//	}

	protected final Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

}
