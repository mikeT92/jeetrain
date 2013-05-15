/* TaskManagerRemote.java 
 */
package edu.hm.cs.fwp.jeetrain.business.tasks.facade;

import java.util.List;

import edu.hm.cs.fwp.jeetrain.business.tasks.model.Task;

/**
 * @author theism
 *
 */
public interface TaskManagerRemote {
	
	public Task addTask(Task newTask);

	public Task retrieveTaskById(long taskId);

	public List<Task> retrieveAllTasks();

}
