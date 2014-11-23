package main.args;

public interface ArgMatcher {
	String getName();
	String getHelp();
	String getHelp(int size);
	String getHelp(int size, char pad);
	boolean isOptional();
	void setOptional(boolean optional);
	boolean matches(String arg);
	void process(String arg);
}
