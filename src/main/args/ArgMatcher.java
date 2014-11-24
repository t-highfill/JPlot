package main.args;

/**
 * General interface for all the argument classes
 * @author Tobias Highfill
 *
 */
public interface ArgMatcher {
	/**
	 * Gets the argument name
	 * @return the name of the argument
	 */
	String getName();
	
	/**
	 * Gets the help text with no padding
	 * @return Help text
	 */
	String getHelp();
	
	/**
	 * Gets the help text and pads the name with spaces until it's length meets "size"
	 * @param size The minimum size of the name
	 * @return	Padded help text
	 */
	String getHelp(int size);
	
	/**
	 * Gets the help text and pads the name with "char" until it's length meets "size"
	 * @param size The minimum size of the name
	 * @param pad The char to pad with
	 * @return Padded help text
	 */
	String getHelp(int size, char pad);
	
	/**
	 * Checks if this argument is optional
	 * @return true if this argument is optional
	 */
	boolean isOptional();
	
	/**
	 * Sets the optionalness (spelling?) of this argument
	 * @param optional The new value
	 */
	void setOptional(boolean optional);
	
	/**
	 * Tests the string to see if it matches the requirements of this argument type
	 * @param arg The string to test
	 * @return True if this string can be processed by this argument
	 */
	boolean matches(String arg);
	
	/**
	 * Do something with the given argument.
	 * @param arg The argument to act on
	 */
	void process(String arg);
}
