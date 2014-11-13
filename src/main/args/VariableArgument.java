package main.args;

public abstract class VariableArgument<E> implements ArgMatcher{
	final String name;
	final E defVal;
	E val;
	
	@Override
	public String getName(){
		return name;
	}
	public VariableArgument(String name, E defVal){
		this(name,defVal, defVal);
	}
	public VariableArgument(String name, E defVal, E val){
		this.name = name;
		this.defVal = defVal;
		this.val = val;
	}
}
