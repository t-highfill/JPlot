package main.args;

public abstract class VariableArgument<E> extends AbstractArgMatcher{
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
		super(name);
		this.defVal = defVal;
		this.val = val;
	}
	public boolean isOptional() {
		return optional;
	}
	public void setOptional(boolean optional) {
		this.optional = optional;
	}
	public E getVal() {
		return val;
	}
}
