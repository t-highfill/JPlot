package main.args;

public interface ArgMatcher {
	String getName();
	boolean matches(String arg);
	void process(String arg);
}
