package main.args;

public class Flag extends Argument<Boolean>{
	
	public Flag(String name){
		this(name, false);
	}

	public Flag(String name, Boolean defVal) {
		super(name, defVal);
	}
	
	public Flag(String name, Boolean defVal, Boolean val) {
		super(name, defVal, val);
	}

	@Override
	public boolean matches(String arg) {
		return this.name.equals(arg);
	}

	@Override
	public void process(String arg) {
		this.val = !this.val;
	}
	
}