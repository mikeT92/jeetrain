/* Conditions.java @(#)%PID%
 */
package edu.hm.cs.fwp.framework.core.dbc;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * Utility class that introduces the design by contract principal into XFRAME
 * applications.
 * <p>
 * All methods to enforce preconditions are named <code>require*</code>, all
 * methods to enforce postconditions are named <code>ensure*</code>. This naming
 * convention is based on the language specification of the Eiffel programming
 * language created by Bertrand Meyer: In Eiffel, <code>require</code> clauses
 * are used to evaluate preconditions and <code>ensure</code> clauses to
 * evaluate postconditions.
 * </p>
 * <p>
 * Please use the following methods depending on the usage scenario:
 * </p>
 * <table border="1">
 * <tr>
 * <td>Preconditions check on parameters</td>
 * <td>Use methods starting with <code>requireParameter*</code></td>
 * </tr>
 * <tr>
 * <td>Preconditions check on properties of Spring managed beans</td>
 * <td>Use methods starting with <code>requireProperty*</code></td>
 * </tr>
 * <tr>
 * <td>Preconditions check not mentioned above</td>
 * <td>Use methods starting with <code>require*</code></td>
 * </tr>
 * <tr>
 * <td>Postconditions check not mentioned above</td>
 * <td>Use methods starting with <code>ensure*</code></td>
 * </tr>
 * </table>
 * 
 * @author p534184
 * @version %PR% %PRT% %PO%
 * @since 22-09-2009 15.38
 */
public final class Conditions {

	private static final String PRE_EQUALS_MESSAGE =
			"Violation of precondition: {0} (expected value=[{1}], actual value=[{2}]) !";

	private static final String POST_EQUALS_MESSAGE =
			"Die Nachbedingung trifft nicht zu: {0} (erwarteter Wert=[{1}], tatsächlicher Wert=[{2}]) !";

	/**
	 * Private default constructor to prevent instantiation.
	 */
	private Conditions() {
	}

	/**
	 * Throws an IllegalArgumentException, if the precondition
	 * <code>parameterValue != null</code> is violated.
	 */
	public static void requireParameterNotNull(Object parameterValue, String parameterName) {
		if (null == parameterValue) {
			throw new IllegalArgumentException(MessageFormat.format(
					"Violation of precondition: Parameter [{0}] must not be null!", parameterName));
		}
	}

	/**
	 * Throws an IllegalArgumentException, if the precondition
	 * <code>parameterType.isAssignableFrom( parameterValue.getClass() )</code>
	 * is violated.
	 */
	public static void requireParameterType(Object parameterValue, String parameterName,
			Class<?> expectedType) {
		if (!expectedType.isAssignableFrom(parameterValue.getClass())) {
			throw new IllegalArgumentException(
					MessageFormat
							.format(
									"Violation of precondition: Type of parameter [{0}] does not match the expected type: (expected type=[{1}], actual type=[{2}]) !",
									parameterName, expectedType.getName(), parameterValue
											.getClass().getName()));
		}
	}

	/**
	 * Throws an IllegalArgumentException, if the precondition
	 * <code>parameterValue != null &amp;&amp; parameterValue.length() != 0</code>
	 * is violated.
	 */
	public static void requireParameterNotEmpty(String parameterValue, String parameterName) {
		requireParameterNotNull(parameterValue, parameterName);
		if (0 == parameterValue.length()) {
			throw new IllegalArgumentException(MessageFormat.format(
					"Violation of precondition: Parameter [{0}] must not be empty!", parameterName));
		}
	}

	/**
	 * Throws an IllegalArgumentException, if the precondition
	 * <code>parameterValue.length != 0</code> is violated.
	 */
	public static void requireOptionalParameterNotEmpty(String parameterValue, String parameterName) {
		if (null != parameterValue && 0 == parameterValue.length()) {
			throw new IllegalArgumentException(MessageFormat.format(
					"Violation of precondition: Optional parameter [{0}] must not be empty!",
					parameterName));
		}
	}

	/**
	 * Throws an IllegalArgumentException, if the precondition
	 * <code>parameterValue != null && parameterValue.length != 0</code> is
	 * violated.
	 */
	public static void requireParameterNotEmpty(Object[] parameterValue, String parameterName) {
		requireParameterNotNull(parameterValue, parameterName);
		if (0 == parameterValue.length) {
			throw new IllegalArgumentException(MessageFormat.format(
					"Violation of precondition: Array parameter [{0}] must not be empty!",
					parameterName));
		}
	}

	/**
	 * Throws an IllegalArgumentException, if the precondition
	 * <code>parameterValue != null && !parameterValue.isEmpty()</code> is
	 * violated.
	 */
	public static void requireParameterNotEmpty(Collection<?> parameterValue, String parameterName) {
		requireParameterNotNull(parameterValue, parameterName);
		if (parameterValue.isEmpty()) {
			throw new IllegalArgumentException(MessageFormat.format(
					"Violation of precondition: Collection parameter [{0}] must not be empty!",
					parameterName));
		}
	}

	/**
	 * Throws an IllegalArgumentException, if the precondition
	 * <code>parameterValue != null && !parameterValue.isEmpty()</code> is
	 * violated.
	 */
	public static void requireParameterNotEmpty(Map<?, ?> parameterValue, String parameterName) {
		requireParameterNotNull(parameterValue, parameterName);
		if (parameterValue.isEmpty()) {
			throw new IllegalArgumentException(MessageFormat.format(
					"Violation of precondition: Map parameter [{0}] must not be empty!",
					parameterName));
		}
	}

	/**
	 * Throws an IllegalArgumentException, if the precondition
	 * <code>parameterValue >= min && parameter <= max</code> is violated.
	 */
	public static void requireParameterInRange(int parameterValue, String parameterName, int min,
			int max) {
		if (!(parameterValue >= min && parameterValue <= max)) {
			throw new IllegalArgumentException(
					MessageFormat
							.format(
									"Violation of precondition: Value of parameter [{0}] exceeds the expected range: (expected range=[{1}..{2}], actual value=[{3}]) !",
									parameterName, Integer.valueOf(min), Integer.valueOf(max),
									Integer.valueOf(parameterValue)));
		}
	}

	/**
	 * Throws an IllegalArgumentException, if the precondition
	 * <code>expected.equals(parameter)</code> is violated.
	 */
	public static void requireParameterEquals(Object parameterValue, String parameterName,
			Object expectedValue) {
		if (!expectedValue.equals(parameterValue)) {
			throw new IllegalArgumentException(
					MessageFormat
							.format(
									"Violation of precondition: Parameter [{0}] must have the expected value: (expected value=[{1}], actual value=[{2}]) !",
									parameterName, expectedValue, parameterValue));
		}
	}

	/**
	 * Throws an IllegalArgumentException if the precondition
	 * <code>parameterValue.length() == expectedLength</code> is violated.
	 */
	public static void requireParameterLength(String parameterValue, String parameterName,
			int expectedLength) {
		int actual = null != parameterValue ? parameterValue.length() : 0;
		if (expectedLength != actual) {
			throw new IllegalArgumentException(
					MessageFormat
							.format(
									"Violation of precondition: Value of parameter [{0}] must have the expected length (expected length=[{1}], actual length=[{2}]) !",
									parameterName, Integer.valueOf(expectedLength), Integer
											.valueOf(actual)));
		}
	}

	/**
	 * Throws an IllegalArgumentException if the precondition
	 * 
	 * <code>parameterValue != null &amp;&amp; parameterValue.length() &lt;= expectedMaxLength</code>
	 * is violated.
	 */
	public static void requireParameterMaxLength(String parameterValue, String parameterName,
			int expectedMaxLength) {
		if (null != parameterValue && parameterValue.length() > expectedMaxLength) {
			throw new IllegalArgumentException(
					MessageFormat
							.format(
									"Violation of precondition: Value [{0}] of parameter [{1}] exceeds maximum length (maximum length=[{2}], actual length=[{3}]) !",
									parameterValue, parameterName, Integer
											.valueOf(expectedMaxLength), Integer
											.valueOf(parameterValue.length())));
		}
	}

	/**
	 * Throws an IllegalArgumentException if the precondition
	 * <code>parameterValue != null &amp;&amp; parameterValue.length &lt;= expectedMaxLength</code>
	 * is violated.
	 */
	public static void requireParameterMaxLength(Object[] parameterValue, String parameterName,
			int expectedMaxLength) {
		if (null != parameterValue && parameterValue.length > expectedMaxLength) {
			throw new IllegalArgumentException(
					MessageFormat
							.format(
									"Violation of precondition: Length of array parameter [{0}] exceeds maximum length (maximum length=[{1}], actual length=[{2}]) !",
									parameterName, Integer.valueOf(expectedMaxLength), Integer
											.valueOf(parameterValue.length)));
		}
	}

	/**
	 * Throws an IllegalArgumentException if the precondition
	 * <code>parameterValue matches expected regular expression</code> is
	 * violated.
	 */
	public static void requireParameterMatches(String parameterValue, String parameterName,
			String expectedPattern) {
		if (null == parameterValue || !Pattern.matches(expectedPattern, parameterValue)) {
			throw new IllegalArgumentException(
					MessageFormat
							.format(
									"Violation of precondition: Value of parameter [{0}] must match the expected pattern (expected pattern=[{1}], actual value=[{2}]) !",
									parameterName, expectedPattern, parameterValue));
		}
	}

	/**
	 * Throws an IllegalArgumentException if the precondition
	 * <code>parameterValue.isNumeric()</code> is violated.
	 */
	public static void requireParameterNumeric(String parameterValue, String parameterName) {
		if (!StringUtils.isNumeric(parameterValue)) {
			throw new IllegalArgumentException(MessageFormat.format(
					"Violation of precondition: Parameter [{0}] must be numeric but was [{1}]!",
					parameterName, parameterValue));
		}
	}

	/**
	 * Throws an IllegalArgumentException if the precondition
	 * <code>property == null</code> is violated.
	 */
	public static void requirePropertyNull(Object property, String propertyName) {
		if (null != property) {
			throw new IllegalStateException(MessageFormat
					.format(
							"Violation of precondition: Property [{0}] can only be set once!",
							propertyName));
		}
	}

	/**
	 * Throws an IllegalStateException, if the precondition
	 * <code>property != null</code> is violated.
	 */
	public static void requirePropertyNotNull(Object property, String propertyName) {

		if (null == property) {
			throw new IllegalStateException(MessageFormat.format(
					"Violation of precondition: Property [{0}] must not be null!", propertyName));
		}
	}

	/**
	 * Throws an IllegalStateException, if the precondition
	 * <code>expectedType.isAssignableFrom( propertyValue.getClass() )</code> is
	 * violated.
	 */
	public static void requirePropertyType(Object propertyValue, String propertyName,
			Class<?> expectedType) {
		if (!expectedType.isAssignableFrom(propertyValue.getClass())) {
			throw new IllegalStateException(
					MessageFormat
							.format(
									"Violation of precondition: Type of property [{0}] does not match the expected type: (expected type=[{1}], actual type=[{2}]) !",
									propertyName, expectedType.getName(), propertyValue
											.getClass().getName()));
		}
	}

	/**
	 * Throws an IllegalStateException, if the precondition
	 * <code>propertyValue != null &amp;&amp; propertyValue.length != 0</code>
	 * is violated.
	 */
	public static void requirePropertyNotEmpty(String propertyValue, String propertyName) {
		requirePropertyNotNull(propertyValue, propertyName);
		if (0 == propertyValue.length()) {
			throw new IllegalStateException(MessageFormat.format(
					"Violation of precondition: Property [{0}] must not be empty!", propertyName));
		}
	}

	/**
	 * Throws an IllegalStateException, if the precondition
	 * <code>propertyValue.length != 0</code> is violated.
	 */
	public static void requireOptionalPropertyNotEmpty(String propertyValue, String propertyName) {
		if (null != propertyValue && 0 == propertyValue.length()) {
			throw new IllegalStateException(MessageFormat.format(
					"Violation of precondition: Optional property [{0}] must not be empty!",
					propertyName));
		}
	}

	/**
	 * Throws an IllegalStateException, if the precondition
	 * <code>propertyValue != null &amp;&amp; property.length != 0</code> is
	 * violated.
	 */
	public static void requirePropertyNotEmpty(Object[] propertyValue, String propertyName) {
		requirePropertyNotNull(propertyValue, propertyName);
		if (0 == propertyValue.length) {
			throw new IllegalStateException(MessageFormat.format(
					"Violation of precondition: Array property [{0}] must not be empty!",
					propertyName));
		}
	}

	/**
	 * Throws an IllegalStateException, if the precondition
	 * <code>propertyValue != null &amp;&amp; property.length != 0</code> is
	 * violated.
	 */
	public static void requirePropertyNotEmpty(Collection<?> propertyValue, String propertyName) {
		requirePropertyNotNull(propertyValue, propertyName);
		if (0 == propertyValue.size()) {
			throw new IllegalStateException(MessageFormat.format(
					"Violation of precondition: Collection property [{0}] must not be empty!",
					propertyName));
		}
	}

	/**
	 * Throws an IllegalStateException, if the precondition
	 * <code>propertyValue != null && !propertyValue.isEmpty()</code> is
	 * violated.
	 */
	public static void requirePropertyNotEmpty(Map<?, ?> propertyValue, String propertyName) {
		requirePropertyNotNull(propertyValue, propertyName);
		if (propertyValue.isEmpty()) {
			throw new IllegalStateException(MessageFormat.format(
					"Violation of precondition: Map property [{0}] must not be empty!",
					propertyName));
		}
	}

	/**
	 * Throws an IllegalStateException, if the precondition
	 * <code>propertyValue &gt;= min && propertyValue &lt;= max</code>. is
	 * violated.
	 */
	public static void requirePropertyInRange(int propertyValue, String propertyName, int min,
			int max) {
		if (!(propertyValue >= min && propertyValue <= max)) {
			throw new IllegalStateException(
					MessageFormat
							.format(
									"Violation of precondition: Value of property [{0}] exceeds the expected range: (expected range=[{1}..{2}], actual value=[{3}]) !",
									propertyName, Integer.valueOf(min), Integer.valueOf(max),
									Integer.valueOf(propertyValue)));
		}
	}

	/**
	 * Throws an IllegalStateException, if the precondition
	 * <code>expectedValue.equals(propertyValue)</code> is violated.
	 */
	public static void requirePropertyEquals(String propertyValue, String propertyName,
			String expectedValue) {
		if (!expectedValue.equals(propertyValue)) {
			throw new IllegalStateException(
					MessageFormat
							.format(
									"Violation of precondition: Property [{0}] must have the expected value: (expected value=[{1}], actual value=[{2}]) !",
									propertyName, expectedValue, propertyValue));
		}
	}

	/**
	 * Throws an IllegalStateException if the precondition
	 * <code>propertyValue.length() == expectedLength</code> is violated.
	 */
	public static void requirePropertyLength(String propertyValue, String propertyName,
			int expectedLength) {
		int actualLength = null != propertyValue ? propertyValue.length() : 0;
		if (expectedLength != actualLength) {
			throw new IllegalStateException(
					MessageFormat
							.format(
									"Violation of precondition: Value of property [{0}] must have the expected length (expected length=[{1}], actual length=[{2}]) !",
									propertyName, Integer.valueOf(expectedLength), Integer
											.valueOf(actualLength)));
		}
	}

	/**
	 * Throws an IllegalStateException if the precondition
	 * <code>propertyValue matches expectedPattern</code> is violated.
	 */
	public static void requirePropertyMatches(String propertyValue, String propertyName,
			String expectedPattern) {
		if (null == propertyValue || !Pattern.matches(expectedPattern, propertyValue)) {
			throw new IllegalStateException(
					MessageFormat
							.format(
									"Violation of precondition: Value of property [{0}] must match the expected pattern (expected pattern=[{1}], actual value=[{2}]) !",
									propertyName, expectedPattern, propertyValue));
		}
	}

	/**
	 * Throws an IllegalStateException if the precondition
	 * <code>expression == true</code> is violated.
	 */
	public static void require(boolean expression, String message) {
		if (!expression) {
			throw new IllegalStateException(MessageFormat.format(
					"Violation of precondition: {0} !", message));
		}
	}

	/**
	 * Throws an IllegalStateException if the precondition
	 * <code>object == null</code> is violated.
	 */
	public static void requireNull(Object object, String message) {
		if (null != object) {
			throw new IllegalStateException(MessageFormat.format(
					"Violation of precondition: {0} (actual value must be null) !", message));
		}
	}

	/**
	 * Throws an IllegalStateException if the precondition
	 * <code>object == null</code> is violated.
	 */
	public static void requireNotNull(Object object, String message) {
		if (null == object) {
			throw new IllegalStateException(MessageFormat.format(
					"Violation of precondition: {0} (actual value must not be null) !", message));
		}
	}

	/**
	 * Throws an IllegalStateException if the precondition
	 * <code>string != null && string.length() != 0</code> is violated.
	 */
	public static void requireNotEmpty(String string, String message) {
		requireNotNull(string, message);
		if (0 == string.length()) {
			throw new IllegalStateException(MessageFormat.format(
					"Violation of precondition: {0} (actual value must not be empty) !", message));
		}
	}

	/**
	 * Throws an IllegalStateException if the precondition
	 * <code>array != null && array.length != 0</code> is violated.
	 */
	public static void requireNotEmpty(Object[] array, String message) {
		requireNotNull(array, message);
		if (0 == array.length) {
			throw new IllegalStateException(MessageFormat.format(
					"Violation of precondition: {0} (actual array value must not be empty) !",
					message));
		}
	}

	/**
	 * Throws an IllegalStateException if the precondition
	 * <code>collection != null && !collection.isEmpty()</code> is violated.
	 */
	public static void requireNotEmpty(Collection<?> collection, String message) {
		requireNotNull(collection, message);
		if (collection.isEmpty()) {
			throw new IllegalStateException(MessageFormat.format(
					"Violation of precondition: {0} (actual collection value must not be empty) !",
					message));
		}
	}

	/**
	 * Throws an IllegalStateException if the precondition
	 * <code>map != null && !map.isEmpty()</code> is violated.
	 */
	public static void requireNotEmpty(Map<?, ?> map, String message) {
		requireNotNull(map, message);
		if (map.isEmpty()) {
			throw new IllegalStateException(MessageFormat.format(
					"Violation of precondition: {0} (actual map value must not be empty) !",
					message));
		}
	}

	/**
	 * Throws an IllegalStateException if the precondition
	 * <code>condition == true</code> is violated.
	 */
	public static void requireTrue(boolean condition, String message) {
		if (!condition) {
			throw new IllegalStateException(MessageFormat.format(
					"Violation of precondition: {0} !", message));
		}
	}

	/**
	 * Throws an IllegalStateException if the precondition
	 * <code>condition == false</code> is violated.
	 */
	public static void requireFalse(boolean condition, String message) {
		if (condition) {
			throw new IllegalStateException(MessageFormat.format(
					"Violation of precondition: {0} !", message));
		}
	}

	/**
	 * Throws an IllegalStateException if the precondition
	 * <code>expected == actual</code> is violated.
	 */
	public static void requireEquals(int expected, int actual, String message) {
		if (expected != actual) {
			throw new IllegalStateException(MessageFormat
					.format(PRE_EQUALS_MESSAGE, message, Integer.valueOf(expected), Integer
							.valueOf(actual)));
		}
	}

	/**
	 * Throws an IllegalStateException if the precondition
	 * <code>expected.equals(actual)</code> is violated.
	 */
	public static void requireEquals(String expected, String actual, String message) {
		if (!expected.equals(actual)) {
			throw new IllegalStateException(MessageFormat.format(
					PRE_EQUALS_MESSAGE, message, expected, actual));
		}
	}

	/**
	 * Throws an IllegalStateException if the precondition
	 * <code>expected.equals(actual)</code> is violated.
	 */
	public static void requireEquals(Object expected, Object actual, String message) {
		if (!expected.equals(actual)) {
			throw new IllegalStateException(MessageFormat.format(
					PRE_EQUALS_MESSAGE, message, expected, actual));
		}
	}

	/**
	 * Throws an IllegalStateException, if the precondition
	 * <code>expectedType.isAssignableFrom( object.getClass() )</code> is
	 * violated.
	 */
	public static void requireType(Object object, Class<?> expectedType, String message) {
		if (!expectedType.isAssignableFrom(object.getClass())) {
			throw new IllegalStateException(
					MessageFormat
							.format(
									"Violation of precondition: {0} Actual type does not match the expected type: (expected type=[{1}], actual type=[{2}]) !",
									expectedType.getName(), object.getClass().getName()));
		}
	}

	/**
	 * Throws an IllegalStateException if the postcondition
	 * <code>expression == true</code> is violated.
	 */
	public static void ensure(boolean expression, String message) {
		if (!expression) {
			throw new IllegalStateException(MessageFormat.format(
					"Violation of postcondition: {0} !", message));
		}
	}

	/**
	 * Throws an IllegalStateException if the postcondition
	 * <code>expected == actual</code> is violated.
	 */
	public static void ensureEquals(int expected, int actual, String message) {
		if (expected != actual) {
			throw new IllegalStateException(MessageFormat.format(
					POST_EQUALS_MESSAGE, message, Integer.valueOf(expected), Integer
							.valueOf(actual)));
		}
	}

	/**
	 * Throws an IllegalStateException if the postcondition
	 * <code>expected.equals(actual)</code> is violated.
	 */
	public static void ensureEquals(String expected, String actual, String message) {
		if (!expected.equals(actual)) {
			throw new IllegalStateException(MessageFormat.format(
					POST_EQUALS_MESSAGE, message, expected, actual));
		}
	}

	/**
	 * Throws an IllegalStateException if the postcondition
	 * <code>object != null</code> is violated.
	 */
	public static void ensureNotNull(Object object, String message) {
		if (null == object) {
			throw new IllegalStateException(MessageFormat.format(
					"Violation of postcondition: {0} (actual value must not be null) !", message));
		}
	}
}
