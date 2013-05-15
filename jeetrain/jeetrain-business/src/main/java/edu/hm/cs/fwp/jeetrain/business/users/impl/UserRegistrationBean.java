/* UserRegistrationBean.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.business.users.impl;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;

import edu.hm.cs.fwp.framework.core.ejb.interceptor.MethodValidationInterceptor;
import edu.hm.cs.fwp.framework.core.ejb.interceptor.TraceInterceptor;
import edu.hm.cs.fwp.jeetrain.business.users.User;
import edu.hm.cs.fwp.jeetrain.business.users.UserRegistration;
import edu.hm.cs.fwp.jeetrain.business.users.UserRegistrationRemote;
import edu.hm.cs.fwp.jeetrain.domain.users.PasswordEncoder;
import edu.hm.cs.fwp.jeetrain.integration.users.UserRepository;

/**
 * Session bean based implementation of a user registration service.
 * 
 * @author Mike
 * @version %PR% %PRT% %PO%
 * @since release 1.0 09.01.2011 16:21:50
 */
@Stateless
@Remote(UserRegistrationRemote.class)
@Local(UserRegistration.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Interceptors({ TraceInterceptor.class, MethodValidationInterceptor.class })
public class UserRegistrationBean implements UserRegistration {

	/**
	 * Resource access component used to store and load {@code User} instances.
	 * Due to the fact that CDI in it's current version does not support
	 * interface inheritance properly, we have to use the @EJB annotation
	 * instead of @Inject.
	 */
	@EJB
	private UserRepository userRepository;

	@Inject
	private PasswordEncoder passwordEncoderService;

	/**
	 * @see eu.unicredit.utrain.business.users.UserRegistration#registerUser(eu.unicredit.utrain.business.users.User)
	 */
	@Override
	public User registerUser(User newUser) {
		if (newUser.getRoles().isEmpty()) {
			throw new IllegalArgumentException(
					"At least one role must be attached to the specified user!");
		}
		newUser.setPassword(this.passwordEncoderService.encode(newUser
				.getPassword()));
		return this.userRepository.addEntity(newUser);
	}

	/**
	 * @see eu.unicredit.utrain.business.users.UserRegistrationRemote#isUserNameAvailable(java.lang.String)
	 */
	@Override
	public boolean isUserIdAvailable(String userId) {
		return this.userRepository.getEntityById(userId) == null;
	}

	/**
	 * @see eu.unicredit.utrain.business.users.UserRegistrationRemote#retrieveUserByName(java.lang.String)
	 */
	@Override
	public User retrieveUserById(String userId) {
		return this.userRepository.getEntityById(userId);
	}

	/**
	 * @see eu.unicredit.utrain.business.users.UserRegistrationRemote#retrieveAllUsers()
	 */
	@Override
	public List<User> retrieveAllUsers() {
		return this.userRepository.queryEntities(User.QUERY_ALL, null);
	}

	/**
	 * @see eu.unicredit.utrain.business.users.UserRegistrationRemote#retrieveAllUsers(int,
	 *      int)
	 */
	@Override
	public List<User> retrieveAllUsers(int startIndex, int pageSize) {
		return this.userRepository.queryEntitiesWithPagination(User.QUERY_ALL,
				null, startIndex, pageSize);
	}

	/**
	 * @see eu.unicredit.utrain.business.users.UserRegistrationRemote#unregisterUser(String)
	 */
	@Override
	public void unregisterUser(String userId) {
		this.userRepository.removeEntityById(userId);
	}
}
