package main.args;

/**
 * This class is for simple flags. when the flag is processed, the value is inverted.
 * @author Tobias.Highfill
 *
 */
public class Flag extends VariableArgument<Boolean>{
	
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
	protected void processArg(String arg) {
		this.val = !this.val;
	}
	
}
