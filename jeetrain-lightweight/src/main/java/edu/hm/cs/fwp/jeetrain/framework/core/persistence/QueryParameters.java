/*
 * QueryParameters.java
 */
package edu.hm.cs.fwp.jeetrain.framework.core.persistence;

import javax.persistence.Query;

/**
 * Repräsentiert eine Liste von Parametern, die an eine Query übergeben werden sollen.
 * @author theism
 * @version 1.0
 * @since 16.04.2015
 */
public interface QueryParameters {
	/**
	 * Übergibt alle Parameter an die angegeben Query.
	 * @param query parametrisierbare Query
	 */
    void applyParameters(Query query);
}