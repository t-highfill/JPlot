package main.args;

import java.awt.Color;

import main.GLColor;

public class ColorOption extends Option<GLColor> {

	public ColorOption(String name, final GLColor defVal) {
		this(name, defVal, defVal);
	}

	public ColorOption(String name, final GLColor defVal, GLColor val) {
		super(name, defVal, val, new Converter<String, GLColor>(){
			@Override
			public GLColor convert(String t) {
				return new GLColor(Color.getColor(t, defVal.toColor()));
			}
		});
	}

}
