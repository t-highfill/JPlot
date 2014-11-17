package main.args;

public class GenericArgument extends VariableArgument<String> {

	public GenericArgument(String name, String defVal) {
		super(name, defVal);
	}

	public GenericArgument(String name, String defVal, String val) {
		super(name, defVal, val);
	}

	@Override
	public boolean matches(String arg) {
		return true;
	}

	@Override
	protected void processArg(String arg) {
		this.val=arg;
	}

}
