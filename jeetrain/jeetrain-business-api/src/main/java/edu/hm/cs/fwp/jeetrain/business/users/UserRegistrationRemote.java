/* UserRegistrationRemote.java 
 */
package edu.hm.cs.fwp.jeetrain.business.users;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Remote view of service facade {@code UserRegistration}
 * 
 * @author theism
 */
public interface UserRegistrationRemote {

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
