/* PasswordEncoderBean.java @(#)%PID%
 */
package edu.hm.cs.fwp.jeetrain.business.users.control;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

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

	private static final String DEFAULT_DIGEST_ALGORITHM = "MD5";
	
	private String digestAlgorithm = DEFAULT_DIGEST_ALGORITHM;
	
	/**
	 * Return the specified password after encoding.
	 */
	public String encode(String password) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance(this.digestAlgorithm);
		} catch (NoSuchAlgorithmException ex) {
			throw new IllegalStateException("Unable to encrypt password", ex);
		}
		md.reset();

		byte[] encryptedPassword = md.digest(password.getBytes());
		return new String(Base64.getEncoder().encode(encryptedPassword), StandardCharsets.UTF_8);
	}
}
