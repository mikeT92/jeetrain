package edu.hm.cs.fwp.jeetrain.business.tasks.entity;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Calendar;
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
 * Komponenten-Test, der das korrekte Persistieren von {@link Task}-Entitäten
 * sicherstellen soll.
 * 
 * @author theism
 *
 */
@RunWith(Arquillian.class)
public class TaskComponentTest {

	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap.create(JavaArchive.class).addPackage(Task.class.getPackage())
				.addClass(GenericRepositoryBean.class).addPackage(AbstractGenericRepository.class.getPackage())
				.addAsManifestResource("arquillian-persistence.xml", "persistence.xml")
				.addAsManifestResource("arquillian-beans.xml", "beans.xml");
	}

	@Inject
	GenericRepositoryBean repository;

	private List<Long> trashCan = new ArrayList<>();
	
	@After
	public void onAfter() {
		for (long userId : this.trashCan) {
			try {
				this.repository.removeEntityById(Task.class, userId);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		trashCan.clear();
	}

	@Test
	public void testAddAndGet() {
		Task newTask = createDummyTask();
		this.repository.addEntity(newTask);
		this.trashCan.add(newTask.getId());
		Task persistentTask = this.repository.getRequiredEntityById(Task.class, newTask.getId());
		assertNotNull(persistentTask);
		assertEquals(newTask.getId(), persistentTask.getId());
	}

	private Task createDummyTask() {
		Task result = new Task();
		result.setSubject("subject");
		result.setDescription("description");
		result.setCategory(TaskCategory.BUGFIX);
		result.setPriority(TaskPriority.LOW);
		result.trackCreation("mtheis", Calendar.getInstance().getTime());
		return result;
	}
}
