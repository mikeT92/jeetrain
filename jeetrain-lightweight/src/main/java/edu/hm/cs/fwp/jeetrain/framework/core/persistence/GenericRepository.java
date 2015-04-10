package edu.hm.cs.fwp.jeetrain.framework.core.persistence;

import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.constraints.NotNull;

/**
 * Generic {@code Repository} managing persistent entities of type {@code T}
 * using key type {@code K}.
 * 
 * @param <K>
 *            entity identifier class (a.k.a. primary key class)
 * @param <T>
 *            entity class
 * 
 * @author p534184
 * @version %PR% %PRT% %PO%
 * @since release 1.0 09.03.2012 10:12:01
 */
public interface GenericRepository<K, T> {

	/**
	 * Adds a new entity to the repository.
	 * 
	 * @param entity
	 *            new entity to be added
	 * @return entity potentially updated with values generated by the
	 *         persistence layer or underlying datastore (like generated primary
	 *         keys, last update timestamps etc.)
	 */
	public T addEntity(@NotNull T entity);

	/**
	 * Returns the entity with the given unique identifier.
	 * 
	 * @param uniqueId
	 *            unique identifier
	 * @return matching entity, if an entity with the given unique identifier
	 *         exists; {@code null} otherwise}.
	 */
	public T getEntityById(@NotNull K uniqueId);

	/**
	 * Returns the entity with the given unique identifier which is expected to
	 * exist.
	 * 
	 * @param uniqueId
	 *            unique identifier
	 * @return matching entity, if an entity with the given unique identifier
	 *         exists.
	 * @throws NoSuchElementException
	 *             - if no entity with the given unique identifier could be
	 *             found.
	 */
	public T getRequiredEntityById(@NotNull K uniqueId);

	/**
	 * Synchronizes the given entity with its persistent representation stored
	 * in the underlying datastore.
	 * 
	 * @param entity
	 *            entity to be synchronized with its persistent state.
	 */
	public T setEntity(@NotNull T entity);

	/**
	 * Removes the given entity from the repository.
	 * 
	 * @param entity
	 *            entity to be removed
	 * 
	 * @throws ConcurrentModificationException
	 *             - if the entity has been modified since the last time it was
	 *             retrieved.
	 */
	public void removeEntity(@NotNull T entity);

	/**
	 * Removes the entity with the specified key from the repository.
	 * <p>
	 * Please note, that using this method will remove the entity referenced by
	 * the specified key in any case disregarding any concurrent modifications.
	 * If you want to ensure that optimistic locking checks are executed before
	 * the entity is actually removed, use method {@link #removeEntity(Object)}
	 * instead.
	 * </p>
	 * 
	 * @param uniqueId
	 *            unique identifier of the entity to be removed
	 */
	public void removeEntityById(@NotNull K uniqueId);

	/**
	 * Returns a single entity that matches the specified query using the
	 * specified parameters.
	 * 
	 * @param queryName
	 *            name of a {@code NamedQuery}
	 * @param queryParameters
	 *            optional parameters to be passed to the query, may be
	 *            {@code null}
	 * @return single entity that matched the query or {@code null}
	 */
	public T queryEntity(@NotNull String queryName,
			List<QueryParameter> queryParameters);

	/**
	 * Returns all entities that match the specified query using the specified
	 * parameters.
	 * 
	 * @param queryName
	 *            name of a {@code NamedQuery}
	 * @param queryParameters
	 *            optional parameters to be passed to the query, may be
	 *            {@code null}
	 * @return {@code List} of entities that matched the query; may be empty but
	 *         never {@code null}
	 */
	public List<T> queryEntities(@NotNull String queryName,
			List<QueryParameter> queryParameters);

	/**
	 * Returns all entities that match the specified query using the specified
	 * parameters starting from a first position using the given page size.
	 * 
	 * @param queryName
	 *            name of a {@code NamedQuery}
	 * @param queryParameters
	 *            optional parameters to be passed to the query, may be
	 *            {@code null}
	 * @param firstPosition
	 *            position of the first entity on the page starting from 0
	 * @param pageSize
	 *            maximum number of entities per page
	 * @return {@code List} of entities included in the specified page; may be
	 *         empty but never {@code null}
	 */
	public List<T> queryEntitiesWithPagination(@NotNull String queryName,
			List<QueryParameter> queryParameters, int firstPosition,
			int pageSize);

	/**
	 * Counts the number of entities that match the specified query using the
	 * specified parameters.
	 * 
	 * @param queryName
	 *            name of a {@code NamedQuery} that contains a
	 *            {@code SELECT COUNT(*)} clause
	 * @param queryParameters
	 *            optional parameters to be passed to the query, may be
	 *            {@code null}
	 * @return number of entities matching query
	 */
	public int countEntities(@NotNull String queryName,
			List<QueryParameter> queryParameters);
}
