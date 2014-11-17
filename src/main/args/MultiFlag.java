package main.args;

import java.util.LinkedList;
import java.util.List;

public class MultiFlag extends AbstractArgMatcher{
	
	private static CharFlag[] breakUp(char[] chars){
		CharFlag[] flags = new CharFlag[chars.length];
		for(int i=0;i<flags.length;i++){
			flags[i] = new CharFlag(chars[i]);
		}
		return flags;
	}
	
	final CharFlag[] flags;
	private List<CharFlag> matchlist = null;
	
	public MultiFlag(String name, CharFlag... flags){
		super(name);
		this.flags=flags;
	}
	
	public MultiFlag(CharFlag... flags){
		this("MultiFlag", flags);
	}
	
	public MultiFlag(String chars){
		this(breakUp(chars.toCharArray()));
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean matches(String arg) {
		if(arg.length()<2 || arg.charAt(0)!='-' || arg.charAt(1)=='-')
			return false;
		String argflags = arg.substring(1);
		this.matchlist = new LinkedList<CharFlag>();
		for(CharFlag flag: flags){
			if(argflags.contains(flag.name))
				this.matchlist.add(flag);
		}
		return !this.matchlist.isEmpty();
	}

	@Override
	protected void processArg(String arg) {
		assert this.matches(arg);
		for(CharFlag match : matchlist){
			match.process(arg);
		}
	}

	public boolean isOptional() {
		return optional;
	}

	public void setOptional(boolean optional) {
		this.optional = optional;
	}

}
