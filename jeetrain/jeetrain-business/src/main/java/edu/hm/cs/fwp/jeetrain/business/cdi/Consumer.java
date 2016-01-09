/* Consumer.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.business.cdi;

/**
 * Simple service consumer to demonstrate CDI-features.
 * 
 * @author theism
 * @version %PR% %PRT% %PO%
 * @since release 1.0 24.04.2013 22:03:14
 */
public interface Consumer {

	public String useService(String what);
}