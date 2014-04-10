/* TaskRepositoryBeanComponentTest.java 
 */
package edu.hm.cs.fwp.jeetrain.integration.tasks.impl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBException;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import edu.hm.cs.fwp.framework.core.logging.ejb.TraceInterceptor;
import edu.hm.cs.fwp.framework.core.persistence.GenericRepository;
import edu.hm.cs.fwp.jeetrain.business.tasks.entity.Task;
import edu.hm.cs.fwp.jeetrain.integration.tasks.TaskRepository;

/**
 * @author theism
 * 
 */
@RunWith(Arquillian.class)
public class TaskRepositoryBeanComponentTest {

	@EJB
	private TaskRepository underTest;

	private List<Task> trashBin = new ArrayList<Task>();

	// FIXME: private ProgrammaticLogin loginHelper = new ProgrammaticLogin();

	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap
				.create(JavaArchive.class)
				.addClass(TaskRepositoryBean.class)
				.addClass(TaskRepository.class)
				.addClass(TraceInterceptor.class)
				.addPackages(true, GenericRepository.class.getPackage())
				.addPackage("edu.hm.cs.fwp.jeetrain.business.tasks.entity")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
				.addAsManifestResource("persistence-test.xml",
						"persistence.xml")
				.addAsManifestResource("META-INF/orm.xml", "orm.xml");
	}

	@Test
	public void testAddTask() {
		Task newTask = createTask();
		Task persistentTask = this.underTest.addEntity(newTask);
		assertNotNull(persistentTask);
		this.trashBin.add(persistentTask);
		assertTrue(persistentTask.getId() != 0L);
		assertTrue(persistentTask.getVersion() != 0);
		assertNotNull(persistentTask.getCreationDate());
		assertEquals("mtheis", persistentTask.getCreatorId());
		assertNotNull(persistentTask.getLastModificationDate());
		assertEquals("mtheis", persistentTask.getLastModifierId());
	}

	@Test
	public void testGetTaskbyIdWithValidIdReturnsOne() {
		Task newTask = createTask();
		Task persistentTask = this.underTest.addEntity(newTask);
		assertNotNull(persistentTask);
		this.trashBin.add(persistentTask);
		Task foundTask = this.underTest.getEntityById(persistentTask.getId());
		assertNotNull(foundTask);
		assertEquals(persistentTask.getId(), foundTask.getId());
	}

	@Test
	public void testGetTaskbyIdWithInvalidIdReturnsNull() {
		Task foundTask = this.underTest.getEntityById(0L);
		assertNull(foundTask);
	}

	@Test(expected = EJBException.class)
	public void testGetRequiredTaskbyIdWithInvalidIdThrowsException() {
		this.underTest.getRequiredEntityById(0L);
	}

	@Before
	public void onBefore() throws Exception {
		// FIXME: this.loginHelper.login("mtheis", "fwpss2013".toCharArray(),"JEETRAIN-REALM", true);
	}

	@After
	public void onAfter() {
		for (Task current : this.trashBin) {
			try {
				this.underTest.removeEntity(current);
			} catch (Exception ex) {
				// don't care
			}
		}
		this.trashBin.clear();
	}

	private Task createTask() {
		Task result = new Task();
		result.setSubject("test subject");
		result.setDescription("test description");
		result.setResponsibleUserId("mtheis");
		return result;
	}
}
