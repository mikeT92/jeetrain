/*
 * jeedemo-lightweight:TaskEditorBean.java
 * Copyright (c) Michael Theis 2017
 */
package edu.cs.hm.fwp.jeedemo.ports.ui.tasks.controller;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import edu.cs.hm.fwp.jeedemo.core.tasks.boundary.TaskManager;
import edu.cs.hm.fwp.jeedemo.core.tasks.entity.Task;
import edu.cs.hm.fwp.jeedemo.core.tasks.entity.TaskCategory;

/**
 * {@code Controller} für den View {@code editTask}.
 * 
 * @author theism
 * @version 1.0
 * @since 19.05.2017
 */
@Named("taskEditor")
@ViewScoped
public class TaskEditorBean implements Serializable {

	private static final long serialVersionUID = -8285691436916211240L;

	private Task task;

	private long taskId;

	@Inject
	private TaskManager boundary;

	public Task getTask() {
		return task;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	public long getTaskId() {
		return taskId;
	}

	public TaskCategory[] getAvailableCategories() {
		return TaskCategory.values();
	}

	public void beforeRenderView() {
		System.out.println("beforeRenderView taskId [" + this.taskId + "]");
		if (this.task == null) {
			if (this.taskId == 0L) {
				this.task = new Task();
			} else {
				this.task = this.boundary.getTaskById(this.taskId);
				if (this.task == null) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Task with task ID [" + this.taskId + "] does not exist!", "summary"));
					this.task = new Task();
				}
			}
		}
	}

	public String saveTask() {
		System.out.println("saveTask on task [" + this.task + "]");
		this.boundary.submitTask(this.task);
		return "editTask?faces-redirect=true&taskId=" + this.task.getId();
	}
}
