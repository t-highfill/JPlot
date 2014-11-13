package main.args;

public abstract class ActionArg implements ArgMatcher {
	
	final String name;
	
	public ActionArg(String name){
		this.name=name;
	}

	@Override
	public boolean matches(String arg) {
		return this.name.equals(arg);
	}

	@Override
	public void process(String arg) {
		if(this.matches(arg))
			action();
	}
	
	@Override
	public String getName(){
		return name;
	}
	
	public abstract void action();
}
