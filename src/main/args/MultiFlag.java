package main.args;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * This class is for the kind of flag argument in which a number of single character flags are passed at once.
 * 
 * For example, the classic: <code>ls -la</code> is the same as <code>ls -l -a</code>
 * @author Tobias.Highfill
 *
 */
public class MultiFlag extends AbstractArgMatcher{
	
	/**
	 * Transforms a char array into an array of CharFlags
	 * 
	 * @param chars
	 * @return
	 */
	private static CharFlag[] breakUp(char[] chars){
		CharFlag[] flags = new CharFlag[chars.length];
		for(int i=0;i<flags.length;i++){
			flags[i] = new CharFlag(chars[i]);
		}
		return flags;
	}
	
	/**
	 * Generates a name for the argument based on the CharFlags
	 * @param flags Flags to generate from
	 * @return Name for argument
	 */
	private static String buildName(CharFlag[] flags){
		String res = "-";
		for(CharFlag cf : flags){
			res+=cf.flag;
		}
		return res;
	}
	
	final CharFlag[] flags;
	private List<CharFlag> matchlist = null;
	
	/**
	 * Initializes with a name and a series of CharFlags. I used varargs for ease of use.
	 * @param name Argument name
	 * @param flags Varargs of CharFlag instances
	 */
	public MultiFlag(String name, CharFlag... flags){
		super(name);
		this.flags=flags;
	}
	
	/**
	 * <p>Initializes with a generated name and a series of CharFlags. I used varargs for ease of use.</p>
	 * <p>The name is generated as a single dash followed by the chars of all the flags in the order they are given.</p>
	 * @param flags Varargs of CharFlag instances
	 */
	public MultiFlag(CharFlag... flags){
		this(buildName(flags), flags);
	}
	
	/**
	 * Breaks up the string into a series of generic CharFlags and generates a name.
	 * @param chars
	 */
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
			if(argflags.contains(flag.flag+""))
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
	
	public String toString(){
		return this.getClass().getName()+":"+Arrays.toString(this.flags);
	}

}
