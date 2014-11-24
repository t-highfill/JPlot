package main.args;

/**
 * This is a type of argument that allows you to take immediate action when an argument is processed.
 * 
 * This was made specifically for <code>--help</code> and <code>--version</code> type arguments
 * @author Tobias Highfill
 *
 */
public abstract class ActionArg extends AbstractArgMatcher{
	
	/**
	 * Initializes with name
	 * @param name Argument name
	 */
	public ActionArg(String name){
		super(name);
	}

	@Override
	public boolean matches(String arg) {
		return this.name.equals(arg);
	}

	@Override
	protected void processArg(String arg) {
		if(this.matches(arg))
			action();
	}
	
	@Override
	public String getName(){
		return name;
	}
	
	/**
	 * Method for subclass to override. Called when the argument is processed. 
	 */
	public abstract void action();
}
