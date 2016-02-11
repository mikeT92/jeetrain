package edu.hm.cs.fwp.jeesample.presentation.tasks;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import edu.hm.cs.fwp.jeesample.business.tasks.boundary.TaskManagerBean;
import edu.hm.cs.fwp.jeesample.business.tasks.boundary.TaskStoreBean;
import edu.hm.cs.fwp.jeesample.business.tasks.entity.Task;

@Named
@ViewScoped
public class TaskEditorBean implements Serializable {

	private static final long serialVersionUID = -4048399778765082695L;

	private Task task;

	private long taskId = -1L;
	
	@Inject 
	private TaskManagerBean taskManager;
	
	@Inject 
	private TaskStoreBean store;
	
	public void onPreRenderView() {
		System.out.println(getClass().getSimpleName() + "#onPreRenderView");
		System.out.println(getClass().getSimpleName() + "#onPreRenderView TaskManagerBean=[" + this.taskManager + "]");
		if (this.task == null) {
			if (this.taskId != -1L) {
				this.task = this.store.getTask(this.taskId);
			} else {
				this.task = new Task();
			}
		}
	}

	public long getTaskId() {
		return taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	public Task getTask() {
		return this.task;
	}

	public String addTask() {
//		FacesContext.getCurrentInstance().addMessage(
//				null,
//				new FacesMessage(FacesMessage.SEVERITY_INFO,
//						"(INFO) Test summary", "(INFO) Test details"));
//		return null;
		System.out.println(getClass().getSimpleName() + "#addTask");
		this.taskManager.addNewTask(this.task);
		this.store.addTask(this.task);
		return "viewTask?faces-redirect=true&taskId=" + this.task.getId();
	}
}
