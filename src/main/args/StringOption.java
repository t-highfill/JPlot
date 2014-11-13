package main.args;

public class StringOption extends Option<String> {

	public StringOption(String name, String defVal, String val){
		super(name, defVal, val, new Converter<String, String>(){
			@Override
			public String convert(String t) {
				return t;
			}
		});
	}

	public StringOption(String name, String defVal) {
		this(name, defVal, defVal);
	}
}
