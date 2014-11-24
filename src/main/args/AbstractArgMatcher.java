package main.args;

/**
 * Mostly a convenience class to handle broad implementation stuff.
 * @author Tobias.Highfill
 *
 */
public abstract class AbstractArgMatcher implements ContinuousMatcher {
	
	final String name;
	public String description = "";
	boolean optional = false;
	private int uses = 1;
	
	/**
	 * Initializes with just the name
	 * @param name Argument name
	 */
	public AbstractArgMatcher(String name){
		this.name=name;
	}
	
	/**
	 * Initializes with name and optionality
	 * @param name Argument name
	 * @param optional Makes this argument optional
	 */
	public AbstractArgMatcher(String name, boolean optional){
		this(name);
		this.optional = optional;
	}
	
	public String getHelp(int size, char pad){
		String namepad = name;
		while(namepad.length()<size){
			namepad = namepad+pad;
		}
		return namepad+"\t"+this.description+(this.optional?"\t(optional)":"");
	}
	
	public String getHelp(int size){
		return this.getHelp(size, ' ');
	}
	
	public String getHelp(){
		return this.getHelp(0);
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
	
	/**
	 * Gets the remaining uses of this argument (for repetition purposes)
	 * @return Number of uses
	 */
	protected int getUses(){
		return uses;
	}
	
	/**
	 * Decrements the number of uses.
	 */
	protected void decrUses(){
		uses--;
	}
	
	/**
	 * The method that does the actual processing
	 * @param arg Argument to process
	 */
	protected abstract void processArg(String arg);

	@Override
	public final void process(String arg) {
		processArg(arg);
		decrUses();
	}

	@Override
	public boolean isDone() {
		return getUses()<=0;
	}

}
