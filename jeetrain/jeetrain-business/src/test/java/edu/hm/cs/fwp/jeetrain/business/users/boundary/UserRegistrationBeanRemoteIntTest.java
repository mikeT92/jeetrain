/* UserRegistrationBeanRemoteTest.java 
 */
package edu.hm.cs.fwp.jeetrain.business.users.boundary;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.hm.cs.fwp.jeetrain.business.users.boundary.UserRegistration;
import edu.hm.cs.fwp.jeetrain.business.users.entity.Gender;
import edu.hm.cs.fwp.jeetrain.business.users.entity.Role;
import edu.hm.cs.fwp.jeetrain.business.users.entity.Roles;
import edu.hm.cs.fwp.jeetrain.business.users.entity.User;

/**
 * @author theism
 * 
 */
public class UserRegistrationBeanRemoteIntTest {

	private UserRegistration underTest;

	private List<Long> trashCan = new ArrayList<>();

	/**
	 * Test method for
	 * {@link eu.unicredit.utrain.business.users.UserRegistration#registerUser(eu.unicredit.utrain.business.users.model.User)}
	 * .
	 */
	@Test
	public void testRegisterAndRetrieve() {
		User newUser = createStandardUser();
		newUser = this.underTest.registerUser(newUser);
		this.trashCan.add(newUser.getId());
		User registeredUser = this.underTest.retrieveUserById(newUser.getId());
		assertNotNull(registeredUser);
		assertEquals(newUser.getId(), registeredUser.getId());
		assertNotNull(registeredUser.getRoles());
		assertFalse(registeredUser.getRoles().isEmpty());
	}

	/**
	 * Test method for
	 * {@link eu.unicredit.utrain.business.users.UserRegistration#retrieveUserByName(java.lang.String)}
	 * .
	 */
	@Test
	public void testRetrieveUserByName() {
		User newUser = createStandardUser();
		newUser = this.underTest.registerUser(newUser);
		this.trashCan.add(newUser.getId());
		User registeredUser = this.underTest.retrieveUserById(newUser.getId());
		assertNotNull(registeredUser);
		assertEquals(newUser.getId(), registeredUser.getId());
	}

	@Test
	public void testUnregisterUser() {
		User newUser = createStandardUser();
		newUser = this.underTest.registerUser(newUser);
		this.trashCan.add(newUser.getId());
		this.underTest.unregisterUser(newUser.getId());
	}

	@Before
	public void setUp() throws Exception {
		if (this.underTest == null) {
			Properties jndiProperties = new Properties();
			jndiProperties.put("org.omg.CORBA.ORBInitialHost", "MSGN3999I.int.root.msg.ag");
			jndiProperties.put("org.omg.CORBA.ORBInitialPort", "10117");
			Context jndiContext = new InitialContext(jndiProperties);
			// this.underTest = (UserRegistrationRemote) jndiContext
			// .lookup("java:global/jeetrain-app/jeetrain-business-0.0.1-SNAPSHOT/UserRegistrationBean!edu.hm.cs.fwp.jeetrain.business.users.UserRegistrationRemote");
			Object remoteObject = jndiContext
					.lookup("java:global/jeetrain-app/jeetrain-business-0.0.1-SNAPSHOT/UserRegistrationBean!edu.hm.cs.fwp.jeetrain.business.users.UserRegistrationRemote");
			this.underTest = (UserRegistration) PortableRemoteObject
					.narrow(remoteObject, UserRegistration.class);
		}
		this.trashCan.clear();
	}

	@After
	public void tearDown() throws Exception {
		for (long userId : this.trashCan) {
			try {
				this.underTest.unregisterUser(userId);
			} catch (Exception ex) {
			}
		}
	}

	private User createStandardUser() {
		User result = new User();
		result.setUserName(buildRandomUserName());
		result.setPassword("fwpss2013");
		result.setConfirmedPassword(result.getPassword());
		result.setFirstName("Klaus");
		result.setLastName("Mustermann");
		result.setFullName("Mustermann Klaus");
		result.setGender(Gender.MALE);
		result.setDateOfBirth(new GregorianCalendar(1965, 1, 13).getTime());
		result.setEmail("klaus.mustermann@hm.edu");
		result.setPhone("08937846498");
		result.setMobile("01707875474");
		result.getRoles().add(new Role(Roles.JEETRAIN_USER));
		return result;
	}

	private String buildRandomUserName() {
		StringBuilder result = new StringBuilder(16);
		result.append("P");
		result.append(System.nanoTime() % 1000000);
		return result.toString();
	}
}
