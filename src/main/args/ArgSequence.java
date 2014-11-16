package main.args;

import java.util.HashMap;

public abstract class ArgSequence<E extends ArgMatcher> extends AbstractArgMatcher{

	private E current;
	private boolean init = false;
	private boolean done = false;
	private HashMap<String, Object> results = new HashMap<String, Object>();
	private HashMap<String, E> matchers = new HashMap<String, E>();

	public ArgSequence(String name) {
		super(name);
	}
	
	public ArgSequence(String name, boolean optional) {
		super(name, optional);
	}
	
	public abstract boolean hasNext();
	protected abstract E next();
	
	private void putResults(Object obj){
		results.put(current.getName(), obj);
		matchers.put(current.getName(), current);
	}

	@Override
	public boolean matches(String arg) {
		if(!init){
			current=next();
			init=true;
		}else if(done){
			return false;
		}
		while (!current.matches(arg)) {
			if(!current.isOptional()){
				System.err.println(arg);
				throw new MissedArgumentException(current.getName());
			}
			this.putResults(null);
			if (!hasNext()) {
				done=true;
				return false;
			}
			current=next();
		}
		return true;
	}

	@Override
	public void process(String arg) {
		assert this.matches(arg);
		current.process(arg);
		if(current instanceof VariableArgument){
			VariableArgument<?> var = (VariableArgument<?>) current;
			this.putResults(var.val);
		}else{
			this.putResults(null);
		}
		if(hasNext()){
			if(isDone(current))
				current=next();
		}else{
			done=true;
		}
	}
	
	private boolean isDone(E e){
		if(e instanceof ContinuousMatcher){
			return ((ContinuousMatcher) e).isDone();
		}
		return true;
	}
	
	public void showResults(){
		System.out.println(this.toString());
	}

	public HashMap<String, Object> getResults() {
		return new HashMap<String, Object>(results);
	}
	
	public String toString(){
		String res = this.getClass().getName()+"{";
		for(String name : this.results.keySet()){
			Object obj =this.results.get(name);
			if(obj!=null)
				res+="\n\t"+name+':'+obj;
			res+="\n\t"+name+':'+this.matchers.get(name);
		}
		return res+"}";
	}

}
