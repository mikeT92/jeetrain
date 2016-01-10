/* GenericRepositoryBean.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.integration;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import edu.hm.cs.fwp.framework.core.persistence.AbstractGenericRepository;

/**
 * Generic Implementation of a {@code Repository}.
 * 
 * @author p534184
 * @version %PR% %PRT% %PO%
 * @since release 1.0 09.03.2012 10:58:41
 */
@Stateless
public class GenericRepositoryBean extends AbstractGenericRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	protected EntityManager getEntityManager() {
		return this.entityManager;
	}
}
