/*
 * jeedemo-lightweight:TaskRepository.java
 * Copyright (c) Michael Theis 2017
 */
package edu.cs.hm.fwp.jeedemo.ports.persistence.tasks;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import edu.cs.hm.fwp.jeedemo.core.tasks.entity.Task;

/**
 * TODO: add documentation!
 * 
 * @author theism
 * @version 1.0
 * @since 19.05.2017
 */
@Stateless
public class TaskRepository {

	@PersistenceContext
	private EntityManager entityManager;

	public long addTask(Task newTask) {
		this.entityManager.persist(newTask);
		return newTask.getId();
	}

	public Task getTaskById(long taskId) {
		return this.entityManager.find(Task.class, taskId);
	}

	public void removeTask(Task task) {
		this.entityManager.remove(task);
	}
}
