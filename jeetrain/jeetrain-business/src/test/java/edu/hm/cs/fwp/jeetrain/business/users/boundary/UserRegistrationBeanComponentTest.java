/* UserRegistrationBeanRemoteTest.java 
 */
package edu.hm.cs.fwp.jeetrain.business.users.boundary;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import edu.hm.cs.fwp.framework.core.logging.ejb.TraceInterceptor;
import edu.hm.cs.fwp.framework.core.persistence.AbstractGenericRepository;
import edu.hm.cs.fwp.framework.core.validation.ejb.MethodValidationInterceptor;
import edu.hm.cs.fwp.jeetrain.business.users.control.PasswordEncoderBean;
import edu.hm.cs.fwp.jeetrain.business.users.entity.Gender;
import edu.hm.cs.fwp.jeetrain.business.users.entity.User;
import edu.hm.cs.fwp.jeetrain.integration.GenericRepositoryBean;

/**
 * @author theism
 * 
 */
@RunWith(Arquillian.class)
public class UserRegistrationBeanComponentTest {

	@Inject
	private UserRegistrationBean underTest;

	private List<String> trashCan = new ArrayList<>();

	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap.create(JavaArchive.class).addClass(UserRegistrationBean.class).addClass(UserRegistration.class)
				.addClass(PasswordEncoderBean.class).addPackage(User.class.getPackage())
				.addClass(TraceInterceptor.class).addClass(MethodValidationInterceptor.class)
				.addClass(GenericRepositoryBean.class).addPackage(AbstractGenericRepository.class.getPackage())
				.addAsManifestResource("arquillian-persistence.xml", "persistence.xml")
				.addAsManifestResource("arquillian-beans.xml", "beans.xml");
	}

	@After
	public void tearDown() throws Exception {
		for (String userId : this.trashCan) {
			try {
				this.underTest.unregisterUser(userId);
			} catch (Exception ex) {
			}
		}
		this.trashCan.clear();
	}

	/**
	 * Test method for
	 * {@link eu.unicredit.utrain.business.users.UserRegistration#registerUser(eu.unicredit.utrain.business.users.model.User)}
	 * .
	 */
	@Test
	public void testRegisterAndRetrieve() {
		User newUser = createStandardUser();
		this.underTest.registerUser(newUser, "jeetrain2016", "jeetrain2016");
		this.trashCan.add(newUser.getId());
		User registeredUser = this.underTest.retrieveUserById(newUser.getId());
		assertNotNull(registeredUser);
		assertEquals(newUser.getId(), registeredUser.getId());
		assertNotNull(registeredUser.getRoles());
		assertFalse(registeredUser.getRoles().isEmpty());
	}

	// @Test
	public void testUnregisterUser() {
		User newUser = createStandardUser();
		this.underTest.registerUser(newUser, "jeetrain2016", "jeetrain2016");
		this.trashCan.add(newUser.getId());
		this.underTest.unregisterUser(newUser.getId());
	}

	private User createStandardUser() {
		User result = new User();
		result.setId(buildRandomUserId());
		result.setFirstName("Klaus");
		result.setLastName("Mustermann");
		result.setFullName("Mustermann Klaus");
		result.setGender(Gender.MALE);
		result.setDateOfBirth(new GregorianCalendar(1965, 1, 13).getTime());
		result.setEmail("klaus.mustermann@hm.edu");
		result.setPhone("08937846498");
		result.setMobile("01707875474");
		result.getRoles().add(this.underTest.retrieveAllRoles().get(0));
		return result;
	}

	private String buildRandomUserId() {
		StringBuilder result = new StringBuilder(16);
		result.append("U");
		result.append(System.nanoTime() % 1000000);
		return result.toString();
	}
}
