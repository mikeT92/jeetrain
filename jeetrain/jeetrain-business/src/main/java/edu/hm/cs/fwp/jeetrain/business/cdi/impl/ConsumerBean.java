/* ConsumerBean.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.business.cdi.impl;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import edu.hm.cs.fwp.jeetrain.business.cdi.Consumer;
import edu.hm.cs.fwp.jeetrain.business.cdi.Service;

/**
 * Simple consumer class managed by CDI.
 * 
 * @author theism
 * @version %PR% %PRT% %PO%
 * @since release 1.0 24.04.2013 21:56:50
 */
@Named
public class ConsumerBean implements Consumer {

	@Inject
	private Service service;
	
	@PostConstruct
	public void onPostConstruct() {
		System.out.println(getClass().getName() + "#onPostConstruct(): service=" + this.service);
	}
	
	/**
	 * @see edu.hm.cs.fwp.jeetrain.business.cdi.Consumer#useService(java.lang.String)
	 */
	@Override
	public String useService(String what) {
		return this.service.doSomething(what);
	}
}
