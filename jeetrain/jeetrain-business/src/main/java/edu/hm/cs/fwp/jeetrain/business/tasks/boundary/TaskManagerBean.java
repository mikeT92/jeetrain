/* TaskManagerBean.java 
 */
package edu.hm.cs.fwp.jeetrain.business.tasks.boundary;

import java.util.List;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import edu.hm.cs.fwp.jeetrain.business.tasks.entity.Task;
import edu.hm.cs.fwp.jeetrain.integration.tasks.TaskRepository;

/**
 * {@code SERVICE FACADE} implementation class.
 * 
 * @author theism
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@RolesAllowed("JEETRAIN_USER")
public class TaskManagerBean implements TaskManager {

	@Resource
	private SessionContext sessionContext;
	
	@EJB
	private TaskRepository taskRepository;

	/**
	 * @see edu.hm.cs.fwp.jeetrain.business.tasks.boundary.TaskManager#addTask(edu.hm.cs.fwp.jeetrain.business.tasks.entity.Task)
	 */
	@Override
	public Task addTask(Task newTask) {
		newTask.setResponsibleUserId(this.sessionContext.getCallerPrincipal().getName());
		return this.taskRepository.addEntity(newTask);
	}

	/**
	 * @see edu.hm.cs.fwp.jeetrain.business.tasks.boundary.TaskManager#retrieveTaskById(long)
	 */
	@Override
	public Task retrieveTaskById(long taskId) {
		return this.taskRepository.getRequiredEntityById(taskId);
	}

	/**
	 * @see edu.hm.cs.fwp.jeetrain.business.tasks.boundary.TaskManager#retrieveAllTasks()
	 */
	@Override
	public List<Task> retrieveAllTasks() {
		return this.taskRepository.queryEntities(Task.QUERY_ALL, null);
	}

}
