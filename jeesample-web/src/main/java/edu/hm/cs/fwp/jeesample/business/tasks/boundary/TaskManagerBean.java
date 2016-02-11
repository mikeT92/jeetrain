package edu.hm.cs.fwp.jeesample.business.tasks.boundary;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Singleton;

import edu.hm.cs.fwp.jeesample.business.tasks.entity.Task;

//@Stateless
@Singleton
@RolesAllowed({"JEETRAIN_USER"})
public class TaskManagerBean {
	
	private final AtomicLong idGenerator = new AtomicLong(1000L);
	
	private Map<Long, Task> tasksById = new HashMap<>();
	
	public long addNewTask(Task task) {
		task.setId(this.idGenerator.incrementAndGet());
		this.tasksById.put(task.getId(), task);
		return task.getId();
	}
	/**
	 * Liefert den Task mit der angegebenen ID zurück.
	 */
	public Task retrieveTaskById(long taskId) {
		return this.tasksById.get(taskId);
	}
}
