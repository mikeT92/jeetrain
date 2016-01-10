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
public class SecurityRoleComponentTest {

	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap.create(JavaArchive.class).addClass(SecurityRole.class)
				.addClass(GenericRepositoryBean.class).addPackage(AbstractGenericRepository.class.getPackage())
				.addAsManifestResource("arquillian-persistence.xml", "persistence.xml")
				.addAsManifestResource("arquillian-beans.xml", "beans.xml");
	}

	@Inject
	GenericRepositoryBean repository;

	private List<Long> trashCan = new ArrayList<>();

	@After
	public void onAfter() {
		for (long securityRoleId : this.trashCan) {
			try {
				this.repository.removeEntityById(SecurityRole.class, securityRoleId);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		trashCan.clear();
	}
	@Test
	public void testAddAndGet() {
		SecurityRole newSecurityRole = new SecurityRole(Roles.JEETRAIN_USER.name());
		this.repository.addEntity(newSecurityRole);
		this.trashCan.add(newSecurityRole.getId());
		SecurityRole persistentSecurityRole = this.repository.getRequiredEntityById(SecurityRole.class, newSecurityRole.getId());
		assertNotNull(persistentSecurityRole);
		assertEquals(newSecurityRole.getId(), persistentSecurityRole.getId());
		assertEquals(newSecurityRole.getName(), persistentSecurityRole.getName());
	}
}
