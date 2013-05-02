/* LocalTest.java @(#)%PID%
 */
package edu.hm.cs.fwp.framework.web.test;

import static org.junit.Assert.*;

import java.util.Locale;

import org.apache.commons.lang.LocaleUtils;
import org.junit.Test;

/**
 * TODO add documentation!
 * 
 * @author Mike
 * @version %PR% %PRT% %PO%
 * @since release ? 08.03.2013 18:35:19
 */
public class ParseLocaleTest {

	@Test(expected=AssertionError.class)
	public void testLocaleforLanguageTagCanParseLocaleToString() {
		Locale parsed = Locale.forLanguageTag(Locale.GERMANY.toString());
		assertEquals(Locale.GERMANY, parsed);
	}

	@Test
	public void testLocaleUtilstoLocaleCanParseLocaleToString() {
		Locale parsed = LocaleUtils.toLocale(Locale.GERMANY.toString());
		assertEquals(Locale.GERMANY, parsed);
	}
}
