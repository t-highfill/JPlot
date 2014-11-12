package main.args;

public abstract class Argument<E> {
	final String name;
	final E defVal;
	E val;
	
	public Argument(String name, E defVal){
		this(name,defVal, defVal);
	}
	public Argument(String name, E defVal, E val){
		this.name = name;
		this.defVal = defVal;
		this.val = val;
	}
	
	public abstract boolean matches(String arg);
	public abstract void process(String arg);
}
