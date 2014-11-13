package main.args;

public class CharFlag extends Flag {
	
	public CharFlag(char name, Boolean defVal, Boolean val) {
		super(""+name, defVal, val);
	}

	public CharFlag(char name, Boolean defVal) {
		super(""+name, defVal);
	}

	public CharFlag(char name){
		super(""+name);
	}
	
	public boolean matches(String arg){
		return arg.length()>=2 && arg.charAt(0)=='-' && arg.charAt(1)!='-' && arg.contains(this.name);
	}

}
