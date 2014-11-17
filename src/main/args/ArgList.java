package main.args;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ArgList<E extends AbstractArgMatcher> extends ArgSequence<E> {
	
	protected final List<E> arglist;

	public ArgList(String name, boolean optional) {
		super(name, optional);
		arglist = new LinkedList<E>();
	}

	public ArgList(String name) {
		this(name, false);
	}
	
	public ArgList(String name, boolean optional, List<E> arglist){
		super(name, optional);
		this.arglist=arglist;
	}
	
	public ArgList(String name, List<E> arglist){
		super(name);
		this.arglist=arglist;
	}
	
	public ArgList(String name, E... args){
		this(name, Arrays.asList(args));
	}
	
	public ArgList(String name, boolean optional, E... args){
		this(name, optional, Arrays.asList(args));
	}

	@Override
	protected List<E> getList() {
		return this.arglist;
	}

}
