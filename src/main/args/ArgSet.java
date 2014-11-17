package main.args;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ArgSet<E extends ArgMatcher> extends AbstractArgMatcher  implements ContinuousMatcher{
	
	private static <E extends ArgMatcher> Set<E> createSet(E[] es){
		return new HashSet<E>(Arrays.asList(es));
	}
	
	final Set<E> argset;
	private Set<E> workingset = null;
	private final Map<String, E> knownMatches = new HashMap<String, E>();
	
	public ArgSet(String name, boolean optional, Set<E> argset) {
		super(name, optional);
		this.argset = argset;
	}

	public ArgSet(String name, Set<E> argset) {
		super(name);
		this.argset = argset;
	}
	
	public ArgSet(String name, E... matchers){
		this(name, createSet(matchers));
	}
	
	public ArgSet(String name, boolean optional, E...matchers){
		this(name, optional, createSet(matchers));
	}
	
	private void checkWorkingset(){
		if(this.workingset == null){
			this.workingset = new HashSet<E>(argset);
		}
	}

	@Override
	public boolean matches(String arg) {
		this.checkWorkingset();
		if(this.knownMatches.get(arg)!=null){
			return true;
		}
		for(E e : this.workingset){
			if(e.matches(arg)){
				knownMatches.put(arg, e);
				this.workingset.remove(e);
				return true;
			}
		}
		return false;
	}

	@Override
	protected void processArg(String arg) {
		assert this.matches(arg);
		this.knownMatches.get(arg).process(arg);
	}

	@Override
	protected int getUses() {
		return this.workingset.size();
	}

	@Override
	protected void decrUses() {
		//Nothing!
	}
	public String toString(){
		String res = "ArgSet[";
		for(ArgMatcher am : this.argset){
			res+="\n\t"+am;
		}
		return res+']';
	}
}
