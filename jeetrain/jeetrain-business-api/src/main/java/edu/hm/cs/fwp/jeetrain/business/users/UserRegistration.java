/* UserRegistration.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.business.users;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Local view on the user registration service.
 * 
 * @author Mike
 * @version %PR% %PRT% %PO%
 * @since release 1.0 09.01.2011 16:23:58
 */
public interface UserRegistration {

	/**
	 * Registers the specified user as a new user of the UTRAIN application.
	 * <p>
	 * Preconditions:
	 * </p>
	 * <ul>
	 * <li>User names must be unique.</li>
	 * </ul>
	 * 
	 * @return newly persisted {@code User}
	 */
	public User registerUser(@NotNull @Valid User newUser);

	/**
	 * Retrieves the user with the specified user ID.
	 * 
	 * @throws IllegalStateException
	 *             , if there is no user with the specified user ID.
	 */
	public User retrieveUserById(@NotNull String userId);

	/**
	 * Returns <code>true</code>, if the specified user ID is available;
	 * otherwise <code>false</code>.
	 */
	public boolean isUserIdAvailable(@NotNull String userId);

	/**
	 * Unregisters the user identified by the given user ID.
	 */
	public void unregisterUser(@NotNull String userId);

	/**
	 * Returns all registered users.
	 * 
	 * @return list of users; empty if no users can be found, never
	 *         <code>null</code>.
	 */
	public List<User> retrieveAllUsers();

	/**
	 * Returns the requested number of users starting from the specified start
	 * index.
	 * 
	 * @param startIndex
	 *            index of the first user to retrieve
	 * @param pageSize
	 *            number of users to retrieve in one call
	 * @return list of users starting from the specified index; empty if no
	 *         users can be found, never <code>null</code>.
	 */
	public List<User> retrieveAllUsers(int startIndex, int pageSize);
}
