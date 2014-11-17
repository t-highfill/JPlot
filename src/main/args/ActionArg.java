package main.args;

public abstract class ActionArg extends AbstractArgMatcher{
	
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
	
	public abstract void action();
}
