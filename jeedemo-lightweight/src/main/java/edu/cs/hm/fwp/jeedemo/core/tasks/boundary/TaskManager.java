/*
 * jeedemo-lightweight:TaskManager.java
 * Copyright (c) Michael Theis 2017
 */
package edu.cs.hm.fwp.jeedemo.core.tasks.boundary;

import java.util.Calendar;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import edu.cs.hm.fwp.jeedemo.core.tasks.entity.Task;
import edu.cs.hm.fwp.jeedemo.ports.persistence.tasks.TaskRepository;

/**
 * TODO: add documentation!
 * 
 * @author theism
 * @version 1.0
 * @since 19.05.2017
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class TaskManager {

	@Inject
	private TaskRepository repository;

	public long submitTask(Task newTask) {
		newTask.trackCreation("mtheis", Calendar.getInstance().getTime());
		return this.repository.addTask(newTask);
	}

	public Task getTaskById(long taskId) {
		return this.repository.getTaskById(taskId);
	}

	public void removeTask(Task task) {
		this.repository.removeTask(task);
	}
}
