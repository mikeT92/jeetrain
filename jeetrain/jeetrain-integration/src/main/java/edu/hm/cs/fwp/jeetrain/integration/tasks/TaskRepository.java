/* TaskRepository.java 
 */
package edu.hm.cs.fwp.jeetrain.integration.tasks;

import edu.hm.cs.fwp.framework.core.persistence.GenericRepository;
import edu.hm.cs.fwp.jeetrain.business.tasks.model.Task;

/**
 * {@code Repository} that manages {@code Task} entities.
 * 
 * @author theism
 * @since 1.0
 */
public interface TaskRepository extends GenericRepository<Long, Task> {

}
