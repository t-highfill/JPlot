package main.args;

public class Option<E> extends VariableArgument<E>{
	
	protected final Converter<String, E> conv;

	public Option(String name, E defVal, Converter<String, E> conv) {
		super(name, defVal);
		this.conv=conv;
	}
	
	public Option(String name, E defVal, E val, Converter<String, E> conv) {
		super(name, defVal, val);
		this.conv=conv;
	}
	
	public boolean nameMatches(String arg){
		return arg.startsWith(this.name);
	}

	@Override
	public boolean matches(String arg) {
		String[] split = arg.split("=");
		return split.length>1 && split[0].equals(this.name);
	}

	@Override
	public void process(String arg) {
		assert this.matches(arg);
		this.val = this.conv.convert(arg.substring(arg.indexOf('=')+1));
	}
	
}
