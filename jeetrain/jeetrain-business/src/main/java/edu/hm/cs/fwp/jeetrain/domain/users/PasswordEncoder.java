/* PasswordEncoder.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.domain.users;

/**
 * Service that encodes passwords before they are persisted.
 * 
 * @author Mike
 * @version %PR% %PRT% %PO%
 * @since release 1.0 09.01.2011 22:56:40
 */
public interface PasswordEncoder {

	/**
	 * Return the specified password after encoding.
	 */
	public String encode(String password);
}