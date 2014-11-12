package main.args;

public class Option extends Argument<String>{

	public Option(String name, String defVal) {
		super(name, defVal);
	}
	
	public Option(String name, String defVal, String val) {
		super(name, defVal, val);
	}

	@Override
	public boolean matches(String arg) {
		String[] split = arg.split("=");
		return split.length>1 && split[0].equals(this.name);
	}

	@Override
	public void process(String arg) {
		assert this.matches(arg);
		this.val = arg.substring(arg.indexOf('=')+1);
	}
	
}
