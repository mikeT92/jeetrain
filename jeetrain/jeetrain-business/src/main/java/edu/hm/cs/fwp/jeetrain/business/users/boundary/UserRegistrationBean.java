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
import edu.hm.cs.fwp.framework.core.persistence.QueryParametersBuilder;
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
	public User registerUser(User newUser) {
		if (newUser.getRoles().isEmpty()) {
			throw new IllegalArgumentException(
					"At least one role must be attached to the specified user!");
		}
		newUser.setPassword(this.passwordEncoder.encode(newUser.getPassword()));
		return this.userRepository.addEntity(newUser, User.class);
	}

	@Override
	public boolean isUserNameAvailable(String userName) {
		return this.userRepository.queryEntity(User.QUERY_BY_NAME,
				new QueryParametersBuilder()
						.withParameter("userName", userName).build(), User.class) == null;
	}

	@Override
	public User retrieveUserById(long userId) {
		return this.userRepository.getEntityById(userId, User.class);
	}

	/**
	 * @see eu.unicredit.utrain.business.users.UserRegistrationRemote#retrieveAllUsers()
	 */
	@Override
	public List<User> retrieveAllUsers() {
		return this.userRepository.queryEntities(User.QUERY_ALL, null, User.class);
	}

	/**
	 * @see eu.unicredit.utrain.business.users.UserRegistrationRemote#retrieveAllUsers(int,
	 *      int)
	 */
	@Override
	public List<User> retrieveAllUsers(int startIndex, int pageSize) {
		return this.userRepository.queryEntitiesWithPagination(User.QUERY_ALL,
				null, startIndex, pageSize, User.class);
	}

	/**
	 * @see eu.unicredit.utrain.business.users.UserRegistrationRemote#unregisterUser(String)
	 */
	@Override
	public void unregisterUser(long userId) {
		this.userRepository.removeEntityById(userId, User.class);
	}
}
