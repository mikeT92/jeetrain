/* TaskManagerBean.java 
 */
package edu.hm.cs.fwp.jeetrain.business.tasks.facade.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import edu.hm.cs.fwp.jeetrain.business.tasks.facade.TaskManager;
import edu.hm.cs.fwp.jeetrain.business.tasks.facade.TaskManagerRemote;
import edu.hm.cs.fwp.jeetrain.business.tasks.model.Task;

/**
 * {@code SERVICE FACADE} implementation class.
 * @author theism
 */
@Singleton
@Local(TaskManager.class)
@Remote(TaskManagerRemote.class)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
//@RolesAllowed("JEETRAIN_USER")
public class TaskManagerBean implements TaskManager {

	private final Map<Long, Task> tasksById = new HashMap<Long, Task>();

	private final AtomicLong taskIdSequence = new AtomicLong();

	/**
	 * TODO: add reason for override!
	 * @see edu.hm.cs.fwp.jeetrain.business.tasks.facade.TaskManager#addTask(edu.hm.cs.fwp.jeetrain.business.tasks.model.Task)
	 */
	@Override
	public Task addTask(Task newTask) {
		newTask.setId(this.taskIdSequence.incrementAndGet());
		this.tasksById.put(newTask.getId(), newTask);
		return newTask;
	}

	/**
	 * TODO: add reason for override!
	 * @see edu.hm.cs.fwp.jeetrain.business.tasks.facade.TaskManager#retrieveTaskById(long)
	 */
	@Override
	public Task retrieveTaskById(long taskId) {
		return this.tasksById.get(taskId);
	}

	/**
	 * TODO: add reason for override!
	 * @see edu.hm.cs.fwp.jeetrain.business.tasks.facade.TaskManager#retrieveAllTasks()
	 */
	@Override
	public List<Task> retrieveAllTasks() {
		return new ArrayList<Task>(this.tasksById.values());
	}

}
