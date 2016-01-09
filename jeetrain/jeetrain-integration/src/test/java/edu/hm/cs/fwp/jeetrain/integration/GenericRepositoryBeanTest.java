/* GenericRepositoryBeanTest.java 
 */
package edu.hm.cs.fwp.jeetrain.integration;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import edu.hm.cs.fwp.framework.core.persistence.QueryParameter;
import edu.hm.cs.fwp.framework.core.persistence.QueryParametersBuilder;
import edu.hm.cs.fwp.jeetrain.business.users.entity.Gender;
import edu.hm.cs.fwp.jeetrain.business.users.entity.Role;
import edu.hm.cs.fwp.jeetrain.business.users.entity.Roles;
import edu.hm.cs.fwp.jeetrain.business.users.entity.User;

/**
 * @author Michael Theis
 * 
 */
@RunWith(Arquillian.class)
public class GenericRepositoryBeanTest {

	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap
				.create(JavaArchive.class)
				.addClass(GenericRepositoryBean.class)
				.addClass(QueryParametersBuilder.class)
				.addClass(QueryParameter.class)
				.addPackage("edu.hm.cs.fwp.jeetrain.business.users.entity")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsManifestResource("persistence-users-test.xml",
						"persistence.xml")
				.addAsManifestResource("META-INF/orm.xml", "orm.xml");
	}

	@Inject
	private GenericRepositoryBean underTest;

	private List<User> trashBin = new ArrayList<User>();

	/**
	 * Tests if adding and getting a User works as expected.
	 */
	@Test
	public void testGetUserById() {
		User newUser = createStandardUser();
		User persistentUser = this.underTest.addEntity(newUser, User.class);
		assertNotNull(persistentUser);
		this.trashBin.add(persistentUser);
		User foundUser = this.underTest.getEntityById(persistentUser.getId(),
				User.class);
		assertNotNull(foundUser);
		assertEquals(persistentUser.getId(), foundUser.getId());
	}

	@Test
	public void testGetUserByName() {
		User newUser = createStandardUser();
		User persistentUser = this.underTest.addEntity(newUser, User.class);
		assertNotNull(persistentUser);
		this.trashBin.add(persistentUser);
		User foundUser = this.underTest.queryEntity(
				User.QUERY_BY_NAME,
				new QueryParametersBuilder().withParameter("userName",
						persistentUser.getUserName()).build(), User.class);
		assertNotNull(foundUser);
		assertEquals(persistentUser.getUserName(), foundUser.getUserName());
	}

	/**
	 * Test method for
	 * {@link edu.hm.cs.fwp.framework.core.persistence.impl.AbstractGenericRepository#getEntityById(java.lang.Object)}
	 * .
	 */
	@Test
	public void testGetEntityById() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link edu.hm.cs.fwp.framework.core.persistence.impl.AbstractGenericRepository#getRequiredEntityById(java.lang.Object)}
	 * .
	 */
	@Test
	public void testGetRequiredEntityById() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link edu.hm.cs.fwp.framework.core.persistence.impl.AbstractGenericRepository#removeEntity(java.lang.Object)}
	 * .
	 */
	@Test
	public void testRemoveEntity() {
		fail("Not yet implemented");
	}

	@After
	public void onAfter() {
		for (User current : this.trashBin) {
			try {
				this.underTest.removeEntity(current, User.class);
			} catch (Exception ex) {
				System.err.println("Unable to remove user [" + current + "]");
			}
		}
		this.trashBin.clear();
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
