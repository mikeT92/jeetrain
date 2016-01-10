/**
 * 
 */
package edu.hm.cs.fwp.jeetrain.business.users.entity;

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

import edu.hm.cs.fwp.framework.core.persistence.AbstractGenericRepository;
import edu.hm.cs.fwp.jeetrain.integration.GenericRepositoryBean;

/**
 * Komponenten-Test, der das korrekt Persistieren von {@link User}-Entitäten
 * sicherstellen soll.
 * 
 * @author theism
 *
 */
@RunWith(Arquillian.class)
public class UserComponentTest {

	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap.create(JavaArchive.class).addPackage(User.class.getPackage())
				.addClass(GenericRepositoryBean.class).addPackage(AbstractGenericRepository.class.getPackage())
				.addAsManifestResource("arquillian-persistence.xml", "persistence.xml")
				.addAsManifestResource("arquillian-beans.xml", "beans.xml");
	}

	@Inject
	GenericRepositoryBean repository;

	private List<String> trashCan = new ArrayList<>();

	@After
	public void onAfter() {
		for (String userId : this.trashCan) {
			try {
				this.repository.removeEntityById(User.class, userId);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		trashCan.clear();
	}
	/**
	 * Test method for
	 * {@link eu.unicredit.utrain.business.users.UserRegistration#registerUser(eu.unicredit.utrain.business.users.model.User)}
	 * .
	 */
	@Test
	public void testRegisterAndRetrieve() {
		User newUser = createStandardUser();
		this.repository.addEntity(newUser);
		this.trashCan.add(newUser.getId());
		User persistentUser = this.repository.getRequiredEntityById(User.class, newUser.getId());
		assertNotNull(persistentUser);
		assertEquals(newUser.getId(), persistentUser.getId());
		assertNotNull(persistentUser.getRoles());
		assertFalse(persistentUser.getRoles().isEmpty());
	}

	private User createStandardUser() {
		User result = new User();
		result.setId(buildRandomUserId());
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
		result.append("U");
		result.append(System.nanoTime() % 1000000);
		return result.toString();
	}
}
