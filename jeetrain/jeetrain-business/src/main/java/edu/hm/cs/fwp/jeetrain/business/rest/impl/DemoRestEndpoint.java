/* DemoRestEndpoint.java 
 */
package edu.hm.cs.fwp.jeetrain.business.rest.impl;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * @author theism
 *
 */
@Path("/demoEntity")
public class DemoRestEndpoint {

	@GET
	@Path("/{id}")
	public String getDemoEntityById(@PathParam("id") long entityId) {
		return "hello out there";
	}
}
