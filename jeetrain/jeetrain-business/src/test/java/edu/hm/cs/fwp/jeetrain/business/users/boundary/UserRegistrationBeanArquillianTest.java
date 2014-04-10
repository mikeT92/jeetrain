/* UserRegistrationBeanRemoteTest.java 
 */
package edu.hm.cs.fwp.jeetrain.business.users.boundary;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import edu.hm.cs.fwp.jeetrain.business.users.entity.Gender;
import edu.hm.cs.fwp.jeetrain.business.users.entity.Role;
import edu.hm.cs.fwp.jeetrain.business.users.entity.Roles;
import edu.hm.cs.fwp.jeetrain.business.users.entity.User;

/**
 * @author theism
 * 
 */
@RunWith(Arquillian.class)
public class UserRegistrationBeanArquillianTest {

	@Inject
	private UserRegistrationBean underTest;

	private List<Long> trashCan = new ArrayList<>();

	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap.create(JavaArchive.class).addPackage(
				"edu.hm.cs.fwp.jeetrain.business.users");
	}

	/**
	 * Test method for
	 * {@link eu.unicredit.utrain.business.users.UserRegistration#registerUser(eu.unicredit.utrain.business.users.model.User)}
	 * .
	 */
	@Test
	public void testRegisterAndRetrieve() {
		User newUser = createStandardUser();
		System.out.println(getClass().getSimpleName()
				+ "#testRegisterAndRetrieve: newUser=" + newUser);
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
	//@Test
	public void testRetrieveUserByName() {
		User newUser = createStandardUser();
		newUser = this.underTest.registerUser(newUser);
		this.trashCan.add(newUser.getId());
		User registeredUser = this.underTest.retrieveUserById(newUser.getId());
		assertNotNull(registeredUser);
		assertEquals(newUser.getId(), registeredUser.getId());
	}

	// @Test
	public void testUnregisterUser() {
		User newUser = createStandardUser();
		newUser = this.underTest.registerUser(newUser);
		this.trashCan.add(newUser.getId());
		this.underTest.unregisterUser(newUser.getId());
	}

	@Before
	public void setUp() throws Exception {
		System.out.println(getClass().getSimpleName() + "#setUp: underTest=["
				+ this.underTest + "]");
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
		result.setUserName(buildRandomUserId());
		result.setPassword("fwpss2013");
		result.setConfirmedPassword(result.getConfirmedPassword());
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
