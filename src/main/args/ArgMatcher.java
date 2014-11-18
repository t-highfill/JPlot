package main.args;

public interface ArgMatcher {
	String getName();
	String getHelp();
	boolean isOptional();
	void setOptional(boolean optional);
	boolean matches(String arg);
	void process(String arg);
}
