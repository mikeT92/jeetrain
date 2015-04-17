package edu.hm.cs.fwp.jeesample.business.tasks.boundary;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import edu.hm.cs.fwp.jeesample.business.tasks.entity.Task;

@Named
@SessionScoped
public class TaskStoreBean implements Serializable {

	private static final long serialVersionUID = 6325289475123080214L;
	
	private final AtomicLong idGenerator = new AtomicLong(1000L);
	
	private Map<Long, Task> tasksById = new HashMap<>();
	
	public void addTask(Task task) {
		task.setId(this.idGenerator.incrementAndGet());
		this.tasksById.put(task.getId(), task);
	}
	
	public Task getTask(long taskId) {
		return this.tasksById.get(taskId);
	}
}
