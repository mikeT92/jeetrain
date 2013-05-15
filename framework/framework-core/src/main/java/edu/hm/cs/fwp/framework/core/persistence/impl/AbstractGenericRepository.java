/* AbstractGenericRepository.java @(#)%PID%
 */
package edu.hm.cs.fwp.framework.core.persistence.impl;

import java.util.List;
import java.util.NoSuchElementException;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import edu.hm.cs.fwp.framework.core.persistence.GenericRepository;
import edu.hm.cs.fwp.framework.core.persistence.Page;
import edu.hm.cs.fwp.framework.core.persistence.PageCriteria;
import edu.hm.cs.fwp.framework.core.persistence.QueryParameter;

/**
 * Abstract implementation of {@link GenericRepository} to be extended by
 * concrete implementations.
 * <p>
 * Typically, this abstract class is extended by stateless session bean
 * implementation classes which are supposed to provide a type-specific
 * repository for a specific application.
 * </p>
 * 
 * @param <K>
 *            entity identifier class (a.k.a. primary key class)
 * @param <T>
 *            entity class
 * 
 * @author p534184
 * @version %PR% %PRT% %PO%
 * @since release 1.0 09.03.2012 10:58:41
 */
public abstract class AbstractGenericRepository<K, T> implements GenericRepository<K, T> {

	/**
	 * @see eu.unicredit.xframe.system.core.persistence.GenericRepository#addEntity(java.lang.Object)
	 */
	@Override
	public T addEntity(T entity) {
		EntityManager em = getEntityManager();
		em.persist(entity);
		em.flush();
		em.refresh(entity);
		return entity;
	}

	/**
	 * @see eu.unicredit.xframe.system.core.persistence.GenericRepository#getEntityById(java.lang.Object)
	 */
	@Override
	public T getEntityById(K uniqueId) {
		T result = null;
		EntityManager em = getEntityManager();
		result = em.find(getEntityType(), uniqueId);
		return result;
	}

	/**
	 * @see eu.unicredit.xframe.system.core.persistence.GenericRepository#getRequiredEntityById(java.lang.Object)
	 */
	@Override
	public T getRequiredEntityById(K uniqueId) {
		T result = null;
		result = getEntityById(uniqueId);
		if (result == null) {
			throw new NoSuchElementException("Missing required entity of type ["
					+ getEntityType().getName() + "] with unique identifier [" + uniqueId + "]!");
		}
		return result;
	}

	/**
	 * @see eu.unicredit.xframe.system.core.persistence.GenericRepository#setEntity(java.lang.Object)
	 */
	@Override
	public T setEntity(T entity) {
		T result = null;
		EntityManager em = getEntityManager();
		result = em.merge(entity);
		return result;
	}

	/**
	 * @see eu.unicredit.xframe.system.core.persistence.GenericRepository#removeEntity(java.lang.Object)
	 */
	@Override
	public void removeEntity(T entity) {
		EntityManager em = getEntityManager();
		T mergedEntity = em.merge(entity);
		em.remove(mergedEntity);
	}

	/**
	 * @see eu.unicredit.xframe.system.core.persistence.GenericRepository#removeEntityById(java.lang.Object)
	 */
	@Override
	public void removeEntityById(K uniqueId) {
		EntityManager em = getEntityManager();
		T entity = em.find(getEntityType(), uniqueId);
		if (entity != null) {
			em.remove(entity);
		}
	}

	/**
	 * @see eu.unicredit.xframe.system.core.persistence.GenericRepository#queryEntity(java.lang.String,
	 *      java.util.List)
	 */
	@Override
	public T queryEntity(String queryName, List<QueryParameter> queryParameters) {
		return createNamedQuery(queryName, queryParameters).getSingleResult();
	}

	/**
	 * @see eu.unicredit.xframe.system.core.persistence.GenericRepository#queryEntities(java.lang.String,
	 *      java.util.List)
	 */
	@Override
	public List<T> queryEntities(String queryName, List<QueryParameter> queryParameters) {
		return createNamedQuery(queryName, queryParameters).getResultList();
	}

	/**
	 * @see eu.unicredit.xframe.system.core.persistence.GenericRepository#queryEntitiesWithPagination(java.lang.String,
	 *      java.util.List, int, int)
	 */
	@Override
	public List<T> queryEntitiesWithPagination(String queryName,
			List<QueryParameter> queryParameters, int firstPosition, int pageSize) {
		TypedQuery<T> query = createNamedQuery(queryName, queryParameters);
		query.setFirstResult(firstPosition);
		query.setMaxResults(pageSize);
		return query.getResultList();
	}

	/**
	 * @see edu.hm.cs.fwp.framework.core.persistence.GenericRepository#queryEntitiesWithPagination(java.lang.String, java.lang.String, edu.hm.cs.fwp.framework.core.persistence.PageCriteria)
	 */
	@Override
	public Page<T> queryEntitiesWithPagination(String queryName, String countQueryName,
			PageCriteria pageCriteria) {
		int totalNumberOfEntities = 0;
		if (pageCriteria.getFirstPosition() == 0 && countQueryName != null) {
			totalNumberOfEntities = countEntities(countQueryName, pageCriteria.getQueryParameters());
		}
		TypedQuery<T> query = createNamedQuery(queryName, pageCriteria.getQueryParameters());
		query.setFirstResult(pageCriteria.getFirstPosition());
		query.setMaxResults(pageCriteria.getPageSize());
		List<T> entitiesOnPage = query.getResultList();
		return new Page<T>(entitiesOnPage, pageCriteria.getFirstPosition(), totalNumberOfEntities);
	}

	/**
	 * @see eu.unicredit.xframe.system.core.persistence.GenericRepository#countEntities(java.lang.String,
	 *      java.util.Map)
	 */
	@Override
	public int countEntities(String queryName, List<QueryParameter> queryParameters) {
		int result = -1;

		EntityManager em = getEntityManager();
		TypedQuery<Long> query = em.createNamedQuery(queryName, Long.class);
		if (queryParameters != null && !queryParameters.isEmpty()) {
			for (QueryParameter currentParam : queryParameters) {
				query.setParameter(currentParam.getName(), currentParam.getValue());
			}
		}
		result = query.getSingleResult().intValue();

		return result;
	}

	/**
	 * Returns the {@link EntityManager} to be used for datastore access.
	 * <p>
	 * Since this generic implementation does not want to care about the
	 * retrieval of {@code EntityManager} instances, the lifecycle management
	 * has been delegated to concrete subclasses and has to be implemented by
	 * them.
	 * </p>
	 */
	protected abstract EntityManager getEntityManager();

	/**
	 * Returns the {@link Class} of the entity.
	 * <p>
	 * Some {@code EntityManager} methods expect a {@code Class} object
	 * representing an entity type to be passed a parameter value. Unfortunately
	 * it is not possible to retrieve type information from formal type
	 * parameters of generic types. Therefore, the concrete {@code Class}
	 * instance representing the concrete entity type has to be provided by
	 * concrete subclasses.
	 * </p>
	 */
	protected abstract Class<T> getEntityType();
	
	/**
	 * Creates a named query and sets all specified parameters.
	 */
	private TypedQuery<T> createNamedQuery(String queryName, List<QueryParameter> queryParameters) {
		TypedQuery<T> result = getEntityManager().createNamedQuery(queryName, getEntityType());
		if (queryParameters != null && !queryParameters.isEmpty()) {
			for (QueryParameter currentParam : queryParameters) {
				result.setParameter(currentParam.getName(), currentParam.getValue());
			}
		}
		return result;
	}
}
