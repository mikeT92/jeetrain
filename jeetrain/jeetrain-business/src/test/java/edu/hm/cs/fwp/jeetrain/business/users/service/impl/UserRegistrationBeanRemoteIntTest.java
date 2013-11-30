/* UserRegistrationBeanRemoteTest.java 
 */
package edu.hm.cs.fwp.jeetrain.business.users.service.impl;

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

import edu.hm.cs.fwp.jeetrain.business.users.facade.UserRegistrationRemote;
import edu.hm.cs.fwp.jeetrain.business.users.model.Gender;
import edu.hm.cs.fwp.jeetrain.business.users.model.Role;
import edu.hm.cs.fwp.jeetrain.business.users.model.Roles;
import edu.hm.cs.fwp.jeetrain.business.users.model.User;

/**
 * @author theism
 * 
 */
public class UserRegistrationBeanRemoteIntTest {

	private UserRegistrationRemote underTest;

	private List<String> trashCan = new ArrayList<String>();

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
			this.underTest = (UserRegistrationRemote) PortableRemoteObject
					.narrow(remoteObject, UserRegistrationRemote.class);
		}
		this.trashCan.clear();
	}

	@After
	public void tearDown() throws Exception {
		for (String userName : this.trashCan) {
			try {
				this.underTest.unregisterUser(userName);
			} catch (Exception ex) {
			}
		}
	}

	private User createStandardUser() {
		User result = new User();
		result.setId(buildRandomUserId());
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

	private String buildRandomUserId() {
		StringBuilder result = new StringBuilder(16);
		result.append("P");
		result.append(System.nanoTime() % 1000000);
		return result.toString();
	}
}
