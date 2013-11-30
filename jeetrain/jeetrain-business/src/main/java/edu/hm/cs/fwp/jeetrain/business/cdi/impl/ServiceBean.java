/* ServiceBean.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.business.cdi.impl;

import javax.inject.Named;

import edu.hm.cs.fwp.jeetrain.business.cdi.Service;

/**
 * Simple CDI managed service bean class to demonstrate CDI features.
 * 
 * @author theism
 * @version %PR% %PRT% %PO%
 * @since release 1.0 24.04.2013 22:00:28
 */
@Named
public class ServiceBean implements Service {

	/**
	 * TODO add documentation about reason for override!
	 * @see edu.hm.cs.fwp.jeetrain.business.cdi.Service#doSomething(java.lang.String)
	 */
	@Override
	public String doSomething(String what) {
		return "done: " + what;
	}
}

