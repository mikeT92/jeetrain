/* PasswordEncoderBean.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.business.users.control;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.enterprise.context.Dependent;
import javax.inject.Named;

/**
 * Service that encodes passwords before they are written to the user
 * repository.
 * 
 * @author Mike
 * @version %PR% %PRT% %PO%
 * @since release 1.0 09.01.2011 22:53:48
 */
@Named
@Dependent
public class PasswordEncoderBean {

	private static final char[] HEXADECIMAL = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
			'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * Return the specified password after encoding.
	 */
	public String encode(String password) {

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException ex) {
			throw new IllegalStateException("Unable to encrypt password", ex);
		}
		md.reset();

		byte[] encryptedPassword = md.digest(password.getBytes());
		StringBuilder sb = new StringBuilder(2 * encryptedPassword.length);
		for (byte current : encryptedPassword) {
			int low = (int) (current & 0x0f);
			int high = (int) ((current & 0xf0) >> 4);
			sb.append(HEXADECIMAL[high]);
			sb.append(HEXADECIMAL[low]);
		}
		return sb.toString();
	}
}