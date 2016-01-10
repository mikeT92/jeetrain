/* UserRegistrationBean.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.business.users.boundary;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;

import edu.hm.cs.fwp.framework.core.logging.ejb.TraceInterceptor;
import edu.hm.cs.fwp.framework.core.persistence.NamedQueryParameters;
import edu.hm.cs.fwp.framework.core.validation.ejb.MethodValidationInterceptor;
import edu.hm.cs.fwp.jeetrain.business.users.control.PasswordEncoderBean;
import edu.hm.cs.fwp.jeetrain.business.users.entity.User;
import edu.hm.cs.fwp.jeetrain.integration.GenericRepositoryBean;

/**
 * Session bean based implementation of a user registration service.
 * 
 * @author Mike
 * @version %PR% %PRT% %PO%
 * @since release 1.0 09.01.2011 16:21:50
 */
@Stateless
@Remote(UserRegistration.class)
@LocalBean
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Interceptors({ TraceInterceptor.class, MethodValidationInterceptor.class })
public class UserRegistrationBean implements UserRegistration {

	@Inject
	private GenericRepositoryBean userRepository;

	@Inject
	private PasswordEncoderBean passwordEncoder;

	@Override
	public void registerUser(User newUser) {
		if (newUser.getRoles().isEmpty()) {
			throw new IllegalArgumentException(
					"At least one role must be attached to the specified user!");
		}
		newUser.setPassword(this.passwordEncoder.encode(newUser.getPassword()));
		this.userRepository.addEntity(newUser);
	}

	@Override
	public boolean isUserNameAvailable(String userName) {
		return this.userRepository.queryEntityWithNamedQuery(User.class, User.QUERY_BY_ID,
				new NamedQueryParameters.Builder()
						.withParameter("userName", userName).build()) == null;
	}

	@Override
	public User retrieveUserById(String userId) {
		return this.userRepository.getEntityById(User.class, userId);
	}

	/**
	 * @see eu.unicredit.utrain.business.users.UserRegistrationRemote#retrieveAllUsers()
	 */
	@Override
	public List<User> retrieveAllUsers() {
		return this.userRepository.queryEntitiesWithNamedQuery(User.class, User.QUERY_ALL, null);
	}

	/**
	 * @see eu.unicredit.utrain.business.users.UserRegistrationRemote#retrieveAllUsers(int,
	 *      int)
	 */
	@Override
	public List<User> retrieveAllUsers(int startIndex, int pageSize) {
		return this.userRepository.queryEntitiesWithNamedQuery(User.class, User.QUERY_ALL,
				null, startIndex, pageSize);
	}

	/**
	 * @see eu.unicredit.utrain.business.users.UserRegistrationRemote#unregisterUser(String)
	 */
	@Override
	public void unregisterUser(String userId) {
		this.userRepository.removeEntityById(User.class, userId);
	}
}
