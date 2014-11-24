package main.args;

/**
 * This is for the specific kind of argument in the style --&lt;name&gt;=&lt;value&gt;
 * @author Tobias.Highfill
 *
 * @param <E> The type of the variable
 */
public class Option<E> extends VariableArgument<E>{
	
	protected final Converter<String, E> conv;

	/**
	 * Initializes with a name, a default value, and a converter
	 * @param name Argument name
	 * @param defVal Default value for this variable
	 * @param conv The converter to use
	 */
	public Option(String name, E defVal, Converter<String, E> conv) {
		super(name, defVal);
		this.conv=conv;
	}
	
	/**
	 * Initializes with a name, a default value, an initial value, and a converter
	 * @param name Argument name
	 * @param defVal Default value for this variable
	 * @param val The initial value
	 * @param conv The converter to use
	 */
	public Option(String name, E defVal, E val, Converter<String, E> conv) {
		super(name, defVal, val);
		this.conv=conv;
	}

	@Override
	public boolean matches(String arg) {
		String[] split = arg.split("=");
		return split.length>1 && split[0].equals(this.name);
	}

	@Override
	protected void processArg(String arg) {
		assert this.matches(arg);
		this.val = this.conv.convert(arg.substring(arg.indexOf('=')+1));
		this.defined = true;
		this.changed = !this.val.equals(this.defVal);
	}
	
}
