package edu.hm.cs.fwp.jeetrain.integration.tasks.repository;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import edu.hm.cs.fwp.jeesample.business.tasks.entity.Task;

@Stateless
public class TaskRepositoryBean {

	@PersistenceContext
	EntityManager em;
	
	// INSERT
	public long addTask(Task task) {
		this.em.persist(task);
		return task.getId();
	}
	
	// SELECT
	public Task getTask(long taskId) {
		return this.em.find(Task.class, taskId);
	}
	
	// UPDATE
	public void setTask(Task task) {
		this.em.merge(task);
	}
	
	// DELETE
	public void removeTask(Task task) {
		Task mergedTask = this.em.merge(task);
		this.em.remove(mergedTask);
	}
}
