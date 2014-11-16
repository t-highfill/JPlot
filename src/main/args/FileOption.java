package main.args;

import java.io.File;


public class FileOption extends Option<File> {
	
	public FileOption(String name, File defVal, boolean optional) {
		this(name, defVal);
		this.setOptional(optional);
	}

	public FileOption(String name, File defVal, File val, boolean optional) {
		this(name, defVal, val);
		this.setOptional(optional);
	}

	public FileOption(String name, File defVal) {
		this(name, defVal, defVal);
	}

	public FileOption(String name, File defVal, File val) {
		super(name, defVal, val, new Converter<String, File>(){
			@Override
			public File convert(String t) {
				return new File(t);
			}
		});
	}

}
