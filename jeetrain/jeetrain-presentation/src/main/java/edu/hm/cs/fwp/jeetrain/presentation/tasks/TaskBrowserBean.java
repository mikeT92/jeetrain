/* TaskBrowserManagedBean.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.presentation.tasks;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;

import org.primefaces.model.SortOrder;

import edu.hm.cs.fwp.framework.web.context.ViewScoped;
import edu.hm.cs.fwp.framework.web.faces.component.datatable.SelectableLazyDataTableModel;
import edu.hm.cs.fwp.jeetrain.business.tasks.facade.TaskManager;
import edu.hm.cs.fwp.jeetrain.business.tasks.model.Task;

/**
 * {@code ManagedBean} that manages the browseTasks view.
 * 
 * @author p534184
 * @version %PR% %PRT% %PO%
 * @since release 1.0 31.10.2012 16:23:52
 */
@SuppressWarnings("serial")
@Named("taskBrowser")
@ViewScoped
public class TaskBrowserBean implements Serializable {

	@EJB
	private TaskManager taskStore;

	private static final class TaskDataTableModel extends
			SelectableLazyDataTableModel<Task> {

		private final TaskManager taskStore;

		public TaskDataTableModel(TaskManager taskStore) {
			this.taskStore = taskStore;
		}

		@Override
		public List<Task> load(int first, int pageSize, String sortField,
				SortOrder sortOrder, Map<String, String> filters) {
			List<Task> tasksOnPage = this.taskStore.retrieveAllTasks();
			setRowCount(tasksOnPage.size());
			System.out.println(getClass().getSimpleName() + "#load: found ["
					+ tasksOnPage.size() + "] task(s)...");
			return tasksOnPage;
		}
	}

	private TaskDataTableModel taskModel;

	// event handlers --------------------------------------------------------

	/**
	 * Gets called whenever a new instance of this managed bean has been created
	 * and all dependencies resolved.
	 */
	@PostConstruct
	public void onPostConstruct() {
		System.out.println(getClass().getSimpleName() + "#onPostConstruct()");
	}

	/**
	 * Gets called whenever the associated view is about to be rendered.
	 */
	public void onPreRenderView() {
		System.out.println(getClass().getSimpleName() + "#onPreRenderView()");
		if (this.taskModel == null) {
			this.taskModel = new TaskDataTableModel(this.taskStore);
		}
	}

	// values ----------------------------------------------------------------

	public TaskDataTableModel getTaskModel() {
		return this.taskModel;
	}

	public boolean isEditTaskEnabled() {
		return this.taskModel.isAnyRowSelected();
	}

	public boolean isViewTaskEnabled() {
		return this.taskModel.isAnyRowSelected();
	}

	public boolean isDeleteTaskEnabled() {
		return this.taskModel.isAnyRowSelected();
	}

	// actions ---------------------------------------------------------------

	public String newTask() {
		return "newTask?faces-redirect=true";
	}

	public String editTask() {
		return "editTask?faces-redirect=true" + "&taskId="
				+ this.taskModel.getSelectedRow().getId();
	}

	public String viewTask() {
		return "viewTask?faces-redirect=true" + "&taskId="
				+ this.taskModel.getSelectedRow().getId();
	}

	public String deleteTask() {
		return "browseTasks?faces-redirect=true";
	}

	public String closeView() {
		return "/home/home?faces-redirect=true";
	}
}
