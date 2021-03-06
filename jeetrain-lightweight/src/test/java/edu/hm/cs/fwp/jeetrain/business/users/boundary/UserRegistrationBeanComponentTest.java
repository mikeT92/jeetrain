package edu.hm.cs.fwp.jeetrain.business.users.boundary;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.sun.enterprise.security.ee.auth.login.ProgrammaticLogin;

import edu.hm.cs.fwp.jeetrain.business.users.control.PasswordEncoderBean;
import edu.hm.cs.fwp.jeetrain.business.users.entity.User;
import edu.hm.cs.fwp.jeetrain.framework.core.logging.ejb.TraceInterceptor;
import edu.hm.cs.fwp.jeetrain.framework.core.persistence.GenericRepositoryBean;

@RunWith(Arquillian.class)
public class UserRegistrationBeanComponentTest {

	/**
	 * Baut ein WAR-Modul mit den zu testenden Klassen.
	 * 
	 * @return WAR-Modul
	 */
	@Deployment
	public static WebArchive createDeployment() {
		WebArchive webModule = ShrinkWrap.create(WebArchive.class).addPackage(User.class.getPackage())
				.addClass(UserRegistrationBean.class).addClass(TraceInterceptor.class)
				.addClass(PasswordEncoderBean.class).addPackage(GenericRepositoryBean.class.getPackage())
				.addClass(UserBuilder.class).addAsResource("arquillian-persistence.xml", "META-INF/persistence.xml")
				.addAsResource(EmptyAsset.INSTANCE, "META-INF/beans.xml")
				.addAsWebInfResource("arquillian-web.xml", "web.xml")
				.addAsWebInfResource("arquillian-glassfish-web.xml", "glassfish-web.xml");
		return webModule;
	}

	@Inject
	private UserRegistrationBean underTest;

	private final List<User> trashBin = new ArrayList<>();

	@After
	public void onAfter() {
		for (User current : trashBin) {
			try {
				this.underTest.unregisterUser(current.getUserName());
			} catch (Exception ex) {
				System.err.println();
			}
		}
		trashBin.clear();
	}

	@Test
	public void addUserStoresAllFields() {
		User newUser = new UserBuilder().build();
		this.underTest.registerUser(newUser);
		this.trashBin.add(newUser);
		User persistentUser = this.underTest.retrieveUserById(newUser.getUserName());
		assertNotNull("Hinzugefügter User muss gefunden werden", persistentUser);
		assertEquals("User.dateOfBirth", newUser.getDateOfBirth(), persistentUser.getDateOfBirth());
		assertEquals("User.email", newUser.getEmail(), persistentUser.getEmail());
		assertEquals("User.firstName", newUser.getFirstName(), persistentUser.getFirstName());
		assertEquals("User.fullName", newUser.getFullName(), persistentUser.getFullName());
		assertEquals("User.gender", newUser.getGender(), persistentUser.getGender());
		assertEquals("User.lastName", newUser.getLastName(), persistentUser.getLastName());
		assertEquals("User.mobile", newUser.getMobile(), persistentUser.getMobile());
		assertEquals("User.phone", newUser.getPhone(), persistentUser.getPhone());
		assertEquals("User.roles", newUser.getRoles(), persistentUser.getRoles());
		assertEquals("User.userName", newUser.getUserName(), persistentUser.getUserName());
	}

	@Test
	public void isUserNameAvailableReturnsFalseOnExistingUser() {
		User newUser = new UserBuilder().build();
		this.underTest.registerUser(newUser);
		this.trashBin.add(newUser);
		assertFalse("Username of registered user is no longer available",
				this.underTest.isUserNameAvailable(newUser.getUserName()));
	}

	@Test
	public void isUserNameAvailableReturnsTrueOnNotExistingUser() {
		assertTrue("Username not taken by any registered user is available",
				this.underTest.isUserNameAvailable("blabla"));
	}

	@Test
	public void loginWithRegisteredUserSucceeds() throws Exception {
		User newUser = new UserBuilder().build();
		String unhashedPassword = newUser.getPassword();
		this.underTest.registerUser(newUser);
		this.trashBin.add(newUser);

		// authenticator.login(newUser.getUserName(), newUser.getPassword());
		ProgrammaticLogin login = new ProgrammaticLogin();
		Boolean succeeded = login.login(newUser.getUserName(), unhashedPassword.toCharArray(), "JEETRAIN_REALM", true);
		assertEquals("Login succeeded with registered user", Boolean.TRUE, succeeded);
	}
}
