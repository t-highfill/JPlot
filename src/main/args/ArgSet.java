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
	private final Map<String, E> knownMatches = new HashMap<String, E>();
	private boolean done = false;
	
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

	@Override
	public boolean matches(String arg) {
		if(this.knownMatches.get(arg)!=null){
			return true;
		}
		for(E e : argset){
			if(e.matches(arg)){
				knownMatches.put(arg, e);
				return true;
			}
		}
		this.done = true;
		return false;
	}

	@Override
	public void process(String arg) {
		assert this.matches(arg);
		this.knownMatches.get(arg).process(arg);
	}

	@Override
	public boolean isDone() {
		return done;
	}

}
