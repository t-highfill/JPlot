package main.args;

/**
 * This is an abstract class for any argument that sets a variable to be used by the program
 * @author Tobias.Highfill
 *
 * @param <E> The type of the variable the argument is converted to
 */
public abstract class VariableArgument<E> extends AbstractArgMatcher{
	final E defVal;
	E val;
	protected boolean defined = false;
	protected boolean changed = false;
	
	/**
	 * Initializes with a name and default value
	 * @param name Argument name
	 * @param defVal Default value for this variable
	 */
	public VariableArgument(String name, E defVal){
		this(name,defVal, defVal);
	}
	
	/**
	 * Initializes with a name, a default value, and an initial value
	 * @param name Argument name
	 * @param defVal Default value for this variable
	 * @param val Initial value for variable
	 */
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
	
	/**
	 * Get the current value of the variable
	 * @return The current value
	 */
	public E getVal() {
		return val;
	}
	
	public String toString(){
		return "Var:"+this.name+"="+this.val;
	}
	
	/**
	 * Test if the variable was defined on the commandline
	 * @return true if the variable was defined
	 */
	public boolean isDefined() {
		return defined;
	}
	
	/**
	 * Test if the value was changed
	 * @return true if changed
	 */
	public boolean isChanged() {
		return changed;
	}
}
