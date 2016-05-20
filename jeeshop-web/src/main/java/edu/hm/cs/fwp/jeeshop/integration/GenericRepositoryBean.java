/*
 * GenericRepositoryBean.java
 * jeeshop-web
 */
package edu.hm.cs.fwp.jeeshop.integration;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import edu.hm.cs.fwp.framework.core.persistence.AbstractGenericRepository;

/**
 * TODO: dokumentieren !!!
 * @author theism
 *
 */
@Stateless
public class GenericRepositoryBean extends AbstractGenericRepository {

	@PersistenceContext
	private EntityManager entityManager;
	/** 
	 * TODO: Grund für das Überschreiben kommentieren !!!
	 *
	 * @see edu.hm.cs.fwp.framework.core.persistence.AbstractGenericRepository#getEntityManager()
	 */
	@Override
	protected EntityManager getEntityManager() {
		return this.entityManager;
	}

}
