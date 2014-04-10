/* UserRepository.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.integration.users;

import edu.hm.cs.fwp.framework.core.persistence.GenericRepository;
import edu.hm.cs.fwp.jeetrain.business.users.entity.User;

/**
 * User repository that manages persistent {@link User} instances.
 * 
 * @author Mike
 * @version %PR% %PRT% %PO%
 * @since release 1.0 09.01.2011 20:59:19
 */
public interface UserRepository extends GenericRepository<Long, User> {
}