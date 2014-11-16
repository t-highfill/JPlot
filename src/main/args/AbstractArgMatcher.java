package main.args;

public abstract class AbstractArgMatcher implements ArgMatcher {
	
	final String name;
	boolean optional = false;
	
	public AbstractArgMatcher(String name){
		this.name=name;
	}
	
	public AbstractArgMatcher(String name, boolean optional){
		this(name);
		this.optional = optional;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean isOptional() {
		return this.optional;
	}

	@Override
	public void setOptional(boolean optional) {
		this.optional = optional;
	}

}
