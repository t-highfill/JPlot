package main.args;

public abstract class VariableArgument<E> extends AbstractArgMatcher{
	final E defVal;
	E val;
	protected boolean defined = false;
	protected boolean changed = false;
	
	public VariableArgument(String name, E defVal){
		this(name,defVal, defVal);
	}
	public VariableArgument(String name, E defVal, E val){
		super(name);
		this.defVal = defVal;
		this.val = val;
	}
	public String getHelp(int size, char pad){
		return super.getHelp(size, pad)+" (default: "+defVal+')';
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
	public String toString(){
		return "Var:"+this.name+"="+this.val;
	}
	public boolean isDefined() {
		return defined;
	}
	public boolean isChanged() {
		return changed;
	}
}
