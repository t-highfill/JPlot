package main.args;

public abstract class AbstractArgMatcher implements ContinuousMatcher {
	
	final String name;
	public String description = "";
	boolean optional = false;
	private int uses = 1;
	
	public AbstractArgMatcher(String name){
		this.name=name;
	}
	
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
	
	protected int getUses(){
		return uses;
	}
	
	protected void decrUses(){
		uses--;
	}
	
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
