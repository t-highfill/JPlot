package main.args;

public class IntOption extends Option<Integer> {

	public IntOption(String name, Integer defVal) {
		this(name, defVal, defVal);
	}

	public IntOption(String name, Integer defVal, Integer val) {
		super(name, defVal, val, new Converter<String, Integer>(){
			@Override
			public Integer convert(String t) {
				return Integer.parseInt(t);
			}
		});
	}

}
