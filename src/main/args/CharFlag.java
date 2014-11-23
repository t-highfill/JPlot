package main.args;

public class CharFlag extends Flag {
	
	final char flag;
	
	public CharFlag(char name, Boolean defVal, Boolean val) {
		super("-"+name, defVal, val);
		flag = name;
	}

	public CharFlag(char name, Boolean defVal) {
		super("-"+name, defVal);
		flag = name;
	}

	public CharFlag(char name){
		super("-"+name);
		flag = name;
	}
	
	public boolean matches(String arg){
		return arg.length()>=2 && arg.charAt(0)=='-' && arg.charAt(1)!='-' && arg.contains(this.flag+"");
	}
	
	public String toString(){
		return this.name+':'+this.val;
	}

}
