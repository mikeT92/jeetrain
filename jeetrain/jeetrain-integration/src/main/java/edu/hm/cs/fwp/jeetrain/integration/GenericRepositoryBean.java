/* GenericRepositoryBean.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.integration;

import java.util.List;
import java.util.NoSuchElementException;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import edu.hm.cs.fwp.framework.core.persistence.QueryParameter;

/**
 * Generic Implementation of a {@code Repository}.
 * 
 * @author p534184
 * @version %PR% %PRT% %PO%
 * @since release 1.0 09.03.2012 10:58:41
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class GenericRepositoryBean {

	@PersistenceContext
	private EntityManager entityManager;

	public <T> T addEntity(T entity, Class<T> entityType) {
		EntityManager em = this.entityManager;
		em.persist(entity);
		em.flush();
		em.refresh(entity);
		return entity;
	}

	public <T> T getEntityById(Object uniqueId, Class<T> entityType) {
		T result = null;
		EntityManager em = this.entityManager;
		result = em.find(entityType, uniqueId);
		return result;
	}

	public <T> T getRequiredEntityById(Object uniqueId, Class<T> entityType) {
		T result = null;
		result = getEntityById(uniqueId, entityType);
		if (result == null) {
			throw new NoSuchElementException(
					"Missing required entity of type [" + entityType.getName()
							+ "] with unique identifier [" + uniqueId + "]!");
		}
		return result;
	}

	public <T> T setEntity(T entity, Class<T> entityType) {
		T result = null;
		EntityManager em = this.entityManager;
		result = em.merge(entity);
		return result;
	}

	public <T> void removeEntity(T entity, Class<T> entityType) {
		EntityManager em = this.entityManager;
		T mergedEntity = em.merge(entity);
		em.remove(mergedEntity);
	}

	public <T> void removeEntityById(Object uniqueId, Class<T> entityType) {
		EntityManager em = this.entityManager;
		T entity = em.find(entityType, uniqueId);
		if (entity != null) {
			em.remove(entity);
		}
	}

	public <T> T queryEntity(String queryName,
			List<QueryParameter> queryParameters, Class<T> entityType) {
		T result = null;

		EntityManager em = this.entityManager;
		TypedQuery<T> query = em.createNamedQuery(queryName, entityType);
		if (queryParameters != null && !queryParameters.isEmpty()) {
			for (QueryParameter currentParam : queryParameters) {
				query.setParameter(currentParam.getName(),
						currentParam.getValue());
			}
		}
		result = query.getSingleResult();

		return result;
	}

	public <T> List<T> queryEntities(String queryName,
			List<QueryParameter> queryParameters, Class<T> entityType) {
		EntityManager em = this.entityManager;
		TypedQuery<T> query = em.createNamedQuery(queryName, entityType);
		if (queryParameters != null && !queryParameters.isEmpty()) {
			for (QueryParameter currentParam : queryParameters) {
				query.setParameter(currentParam.getName(),
						currentParam.getValue());
			}
		}
		return query.getResultList();
	}

	public <T> List<T> queryEntitiesWithPagination(String queryName,
			List<QueryParameter> queryParameters, int firstPosition,
			int pageSize, Class<T> entityType) {
		EntityManager em = entityManager;
		TypedQuery<T> query = em.createNamedQuery(queryName, entityType);
		if (queryParameters != null && !queryParameters.isEmpty()) {
			for (QueryParameter currentParam : queryParameters) {
				query.setParameter(currentParam.getName(),
						currentParam.getValue());
			}
		}
		query.setFirstResult(firstPosition);
		query.setMaxResults(pageSize);
		return query.getResultList();
	}

	public int countEntities(String queryName,
			List<QueryParameter> queryParameters) {
		int result = -1;
		TypedQuery<Long> query = this.entityManager.createNamedQuery(queryName,
				Long.class);
		if (queryParameters != null && !queryParameters.isEmpty()) {
			for (QueryParameter currentParam : queryParameters) {
				query.setParameter(currentParam.getName(),
						currentParam.getValue());
			}
		}
		result = query.getSingleResult().intValue();
		return result;
	}
}
