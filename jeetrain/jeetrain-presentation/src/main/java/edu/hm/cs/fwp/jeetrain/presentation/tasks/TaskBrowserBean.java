/* TaskBrowserManagedBean.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.presentation.tasks;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.SortOrder;

import edu.hm.cs.fwp.framework.web.faces.component.datatable.SelectableLazyDataTableModel;
import edu.hm.cs.fwp.jeetrain.business.tasks.boundary.TaskManager;
import edu.hm.cs.fwp.jeetrain.business.tasks.entity.Task;

/**
 * {@code ManagedBean} that manages the browseTasks view.
 * 
 * @author p534184
 * @version %PR% %PRT% %PO%
 * @since release 1.0 31.10.2012 16:23:52
 */
@Named("taskBrowser")
@ViewScoped
public class TaskBrowserBean implements Serializable {

	private static final long serialVersionUID = -4819275650500486442L;
	
	@Inject
	private TaskManager taskStore;

	private static final class TaskDataTableModel extends SelectableLazyDataTableModel<Task> {

		private final TaskManager taskStore;

		private final List<Task> pageItems = new ArrayList<>();

		public TaskDataTableModel(TaskManager taskStore) {
			this.taskStore = taskStore;
		}

		@Override
		public Object getRowKey(Task object) {
			return object.getId();
		}

		@Override
		public Task getRowData(String rowKey) {
			Task result = null;
			if (rowKey != null) {
				Long taskId = Long.valueOf(rowKey);
				for (Task currentRow : this.pageItems) {
					if (currentRow.getId() == taskId) {
						result = currentRow;
						break;
					}
				}
			}
			return result;
		}

		@Override
		public List<Task> load(int first, int pageSize, String sortField, SortOrder sortOrder,
				Map<String, Object> filters) {
			this.pageItems.clear();
			this.pageItems.addAll(this.taskStore.retrieveAllTasks());
			setRowCount(this.pageItems.size());
			return this.pageItems;
		}
	}

	private TaskDataTableModel taskModel;

	// event handlers --------------------------------------------------------

	/**
	 * Gets called whenever the associated view is about to be rendered.
	 */
	public void onPreRenderView() {
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
		return "editTask?faces-redirect=true" + "&taskId=" + this.taskModel.getSelectedRow().getId();
	}

	public String viewTask() {
		return "viewTask?faces-redirect=true" + "&taskId=" + this.taskModel.getSelectedRow().getId();
	}

	public String deleteTask() {
		return "browseTasks?faces-redirect=true";
	}

	public String closeView() {
		return "/home/home?faces-redirect=true";
	}
}
